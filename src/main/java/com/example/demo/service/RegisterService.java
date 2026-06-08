package com.example.demo.service;

import com.example.demo.dto.RegisterReq;
import com.example.demo.dto.RegisterRes;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    // 註冊送的初始點數
    private static final int INITIAL_BALANCE = 1000;

    private final UserRepository userRepository;     // 寫 MySQL
    private final StringRedisTemplate redisTemplate; // 寫 Redis

    // 兩個依賴都由 Spring 自動注入
    public RegisterService(UserRepository userRepository, StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public RegisterRes register(RegisterReq req) {
        String email = req.getEmail();
        String userName =req.getUserName();



        // 1) 建 User，寫進 MySQL。save() 回傳的物件帶有自動產生的 id
        User user = new User(email, userName, INITIAL_BALANCE);
        User saved = userRepository.save(user);

        // 2) 把該 user 的狀態寫進 Redis（用 Hash，一個 key 一包）
        String key = "user:" + saved.getId();
        redisTemplate.opsForHash().put(key, "balance", String.valueOf(INITIAL_BALANCE));
        redisTemplate.opsForHash().put(key, "version", "0");
        redisTemplate.opsForHash().put(key, "freeGameCount", "0");

        // 3) 回傳結果
        return new RegisterRes(saved.getId(), saved.getBalance());
    }
}
