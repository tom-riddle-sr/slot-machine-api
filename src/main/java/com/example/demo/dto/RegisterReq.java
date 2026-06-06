package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// 註冊請求的 DTO：接收前端送來的 email
// 驗證（@NotBlank / @Email）放在這裡，而不是 entity 上
public class RegisterReq {

    @NotBlank(message = "email 不能是空的")
    @Email(message = "email 格式不正確")
    private String email;

    public RegisterReq() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
