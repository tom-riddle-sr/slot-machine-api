package com.example.demo.service;

import com.example.demo.dto.SpinReq;
import com.example.demo.dto.SpinRes;
import com.example.demo.entity.User;
import com.example.demo.game.SymbolGenerator;
import com.example.demo.game.SymbolType;
import com.example.demo.game.WinCalculator;
import com.example.demo.kafka.SpinRecordMessage;
import com.example.demo.kafka.SpinRecordProducer;
import com.example.demo.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SpinService {

    // Lua 樂觀鎖腳本：整段原子執行，不會被別的請求插隊
    // KEYS[1] = Redis key（user:{id}）
    // ARGV[1] = 舊 version，ARGV[2] = 新 balance
    // 比對 Redis 目前 version 是否還等於舊 version：
    //   不一樣 → 回 -1（代表期間被別人改過，樂觀鎖失敗）
    //   一樣   → 更新 balance、version+1，回 1
    private static final String CAS_SCRIPT = """
            local cur = redis.call('HGET', KEYS[1], 'version')
            if cur ~= ARGV[1] then
                return -1
            end
            redis.call('HSET', KEYS[1], 'balance', ARGV[2])
            redis.call('HSET', KEYS[1], 'version', ARGV[1] + 1)
            return 1
            """;

    private final UserRepository userRepository;
    private final SpinRecordProducer spinRecordProducer;
    private final StringRedisTemplate redisTemplate;
    private final SymbolGenerator symbolGenerator;
    private final WinCalculator winCalculator;

    public SpinService(UserRepository userRepository, SpinRecordProducer spinRecordProducer,
                       StringRedisTemplate redisTemplate, SymbolGenerator symbolGenerator,
                       WinCalculator winCalculator) {
        this.userRepository = userRepository;
        this.spinRecordProducer = spinRecordProducer;
        this.redisTemplate = redisTemplate;
        this.symbolGenerator = symbolGenerator;
        this.winCalculator = winCalculator;
    }

    public SpinRes spin(SpinReq req) {
        int userId = req.getUserId();
        int bet = req.getBet();
        String key = "user:" + userId;

        // 1) 從 Redis 讀 balance、version（沒有就從 MySQL 重建快取）
        Object balanceObj = redisTemplate.opsForHash().get(key, "balance");
        Object versionObj = redisTemplate.opsForHash().get(key, "version");
        if (balanceObj == null || versionObj == null) {
            User u = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            redisTemplate.opsForHash().put(key, "balance", String.valueOf(u.getBalance()));
            redisTemplate.opsForHash().put(key, "version", "0");
            balanceObj = String.valueOf(u.getBalance());
            versionObj = "0";
        }
        int balance = Integer.parseInt(balanceObj.toString());
        int version = Integer.parseInt(versionObj.toString());

        // 2) 驗證餘額
        if (balance < bet) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // 3) 產生盤面、4) 算中獎、5) 算新餘額
        List<SymbolType> board = symbolGenerator.spin();
        int win = winCalculator.calculate(board, bet);
        int newBalance = balance - bet + win;

        // 6) 數 Scatter（這裡先只標記，Free Game 之後做）
        boolean isFreeGame = board.stream().filter(SymbolType::isScatter).count() >= 3;

        // 7) 樂觀鎖更新 Redis（Lua 原子比對 version）
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(CAS_SCRIPT, Long.class);
        Long result = redisTemplate.execute(script, List.of(key),
                String.valueOf(version), String.valueOf(newBalance));
        if (result == null || result == -1) {
            throw new RuntimeException("請稍後再試"); // version 對不上，代表並發衝突
        }

        // 8) Redis 更新成功後，同步寫 MySQL 的 balance
        List<String> symbolList = board.stream().map(SymbolType::getEmoji).toList();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        user.setBalance(newBalance);
        userRepository.save(user);

        // 8-2) spin 紀錄改走 Kafka 非同步寫（不卡玩家，consumer 在背景寫 MySQL）
        spinRecordProducer.send(
                new SpinRecordMessage(userId, symbolList, bet, win, isFreeGame, 0));

        // 9) 回傳
        return new SpinRes(symbolList, userId, win, newBalance, isFreeGame);
    }
}
