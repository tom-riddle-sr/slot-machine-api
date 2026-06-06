package com.example.demo.dto;

public class RegisterRes {
    private Integer userId;
    private Integer balance;


    public RegisterRes() {
    }

    public RegisterRes(Integer userId, Integer balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
