package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    public UserService(UserRepository userRepository, StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
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

}