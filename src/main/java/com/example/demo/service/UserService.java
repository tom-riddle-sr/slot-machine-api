package com.example.demo.service;

import com.example.demo.dto.HistoryRes;
import com.example.demo.dto.SpinRecordDto;
import com.example.demo.entity.SpinRecord;
import com.example.demo.entity.User;
import com.example.demo.repository.SpinRecordRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SpinRecordRepository spinRecordRepository;
    private final StringRedisTemplate redisTemplate;

    public UserService(UserRepository userRepository, SpinRecordRepository spinRecordRepository,
                       StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.spinRecordRepository = spinRecordRepository;
        this.redisTemplate = redisTemplate;
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return "刪除成功";
    }

    public int getBalance(int id) {
        // 1) 先從 Redis 讀
        Object cached = redisTemplate.opsForHash().get("user:" + id, "balance");

        if (cached != null) {
            // Redis 有 → 直接用（字串轉 int）
            return Integer.parseInt(cached.toString());
        }

        // 2) Redis 沒有 → 從 MySQL 讀
        User user = userRepository.findById(id).orElseThrow();
        int balance = user.getBalance();

        // 3) 順便把 Redis 重建（field 是 "balance"，value 是餘額字串）
        redisTemplate.opsForHash().put("user:" + id, "balance", String.valueOf(balance));

        // 4) 回傳
        return balance;
    }

    public HistoryRes getHistory(int userId, int page, int size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        
        Pageable pageable = PageRequest.of(page, size);
        Page<SpinRecord> result = spinRecordRepository.findByUserId(userId, pageable);

        return new HistoryRes(userId, result.getContent().stream()
                .map(r -> new SpinRecordDto(r.getSymbols(), r.getBet(), r.getWin(),
                        r.isFreeGame(), r.getFreeGameRound(), r.getCreatedAt()))
                .toList());
    }

}