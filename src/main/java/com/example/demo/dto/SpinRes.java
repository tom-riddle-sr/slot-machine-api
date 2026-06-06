package com.example.demo.dto;

import java.util.List;

public class SpinRes {

    private List<String> symbolList;
    private Integer userId;
    private Integer win;
    private Integer balance;
    private boolean isFreeGame;

    public SpinRes() {
    }

    public SpinRes(List<String> symbolList, Integer userId, Integer win,
                   Integer balance, boolean isFreeGame) {
        this.symbolList = symbolList;
        this.userId = userId;
        this.win = win;
        this.balance = balance;
        this.isFreeGame = isFreeGame;
    }

    public List<String> getSymbolList() {
        return symbolList;
    }

    public void setSymbolList(List<String> symbolList) {
        this.symbolList = symbolList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public boolean isFreeGame() {
        return isFreeGame;
    }

    public void setFreeGame(boolean isFreeGame) {
        this.isFreeGame = isFreeGame;
    }
}
