package com.example.demo.controller;


import com.example.demo.dto.SpinReq;
import com.example.demo.dto.SpinRes;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpingController {

    // TODO: 之後接上 SpinService
    @PostMapping("/spin")
    public SpinRes spin(@Valid @RequestBody SpinReq spinReq) {
        return null;
    }
}
