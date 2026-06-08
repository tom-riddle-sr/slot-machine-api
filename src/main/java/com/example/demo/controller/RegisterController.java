package com.example.demo.controller;

import com.example.demo.dto.RegisterReq;
import com.example.demo.dto.RegisterRes;
import com.example.demo.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/Register")
    public RegisterRes register(@Valid @RequestBody RegisterReq req) {
        return registerService.register(req);
    }


}
