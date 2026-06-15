package com.example.demo.controller;


import com.example.demo.dto.SpinReq;
import com.example.demo.dto.SpinRes;
import com.example.demo.service.SpinService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpingController {
    private final SpinService spinService;


    public SpingController(SpinService spinService) {
        this.spinService = spinService;
    }

    @PostMapping("/spin")
    public SpinRes spin(@Valid @RequestBody SpinReq spinReq) {
        return spinService.spin(spinReq);
    }
}
