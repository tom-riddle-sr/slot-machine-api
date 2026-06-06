package com.example.demo.dto;

// 查餘額的回應
public class BalanceRes {

    private Integer userId;
    private Integer balance;

    public BalanceRes() {
    }

    public BalanceRes(Integer userId, Integer balance) {
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
