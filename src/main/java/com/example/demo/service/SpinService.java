package com.example.demo.service;

import com.example.demo.dto.SpinReq;
import com.example.demo.dto.SpinRes;
import com.example.demo.entity.SpinRecord;
import com.example.demo.entity.User;
import com.example.demo.game.SymbolGenerator;
import com.example.demo.game.SymbolType;
import com.example.demo.game.WinCalculator;
import com.example.demo.repository.SpinRecordRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SpinService {
    private final UserRepository userRepository;
    private final SpinRecordRepository spinRecordRepository;
    private final StringRedisTemplate redisTemplate;
    private final SymbolGenerator symbolGenerator;
    private final WinCalculator winCalculator;

    public SpinService(UserRepository userRepository, SpinRecordRepository spinRecordRepository,
                       StringRedisTemplate redisTemplate, SymbolGenerator symbolGenerator,
                       WinCalculator winCalculator) {
        this.userRepository = userRepository;
        this.spinRecordRepository = spinRecordRepository;
        this.redisTemplate = redisTemplate;
        this.symbolGenerator = symbolGenerator;
        this.winCalculator = winCalculator;
    }

    public SpinRes spin(SpinReq req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + req.getUserId()));

        int balance = user.getBalance();
        int bet = req.getBet();

        if (balance < bet) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        List<SymbolType> board = symbolGenerator.spin();
        int win = winCalculator.calculate(board, bet);

        int total = balance - bet + win;
        user.setBalance(total);
        userRepository.save(user);

        redisTemplate.opsForHash().put("user:" + user.getId(), "balance", String.valueOf(total));

        boolean isFreeGame = false;
        for (SymbolType s : board) {
            if (s.isScatter()) {
                isFreeGame = true;
                break;
            }
        }

        List<String> symbolList = board.stream().map(SymbolType::getEmoji).toList();

        spinRecordRepository.save(new SpinRecord(user.getId(), symbolList, bet, win, isFreeGame, 0));

        return new SpinRes(symbolList, user.getId(), win, total, isFreeGame);
    }
}
