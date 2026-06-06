package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

// 歷史紀錄裡的「精簡版」旋轉紀錄
// 只回傳前端需要看的欄位，不含 id / userId（那些是內部用的）
public class SpinRecordDto {

    private List<String> symbols;
    private Integer bet;
    private Integer win;
    private Boolean isFreeGame;
    private Integer freeGameRound;
    private LocalDateTime createdAt;

    public SpinRecordDto() {
    }

    public SpinRecordDto(List<String> symbols, Integer bet, Integer win,
                         Boolean isFreeGame, Integer freeGameRound, LocalDateTime createdAt) {
        this.symbols = symbols;
        this.bet = bet;
        this.win = win;
        this.isFreeGame = isFreeGame;
        this.freeGameRound = freeGameRound;
        this.createdAt = createdAt;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }

    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Boolean getIsFreeGame() {
        return isFreeGame;
    }

    public void setIsFreeGame(Boolean isFreeGame) {
        this.isFreeGame = isFreeGame;
    }

    public Integer getFreeGameRound() {
        return freeGameRound;
    }

    public void setFreeGameRound(Integer freeGameRound) {
        this.freeGameRound = freeGameRound;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
