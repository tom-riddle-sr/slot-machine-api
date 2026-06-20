package com.example.demo.kafka;

import java.util.List;

// 送到 Kafka 的訊息：一筆 spin 紀錄需要的所有資料
// 需要無參數建構子 + getter/setter，JSON 序列化/反序列化才能運作
public class SpinRecordMessage {

    private int userId;
    private List<String> symbols;
    private int bet;
    private int win;
    private boolean freeGame;
    private int freeGameRound;

    public SpinRecordMessage() {
    }

    public SpinRecordMessage(int userId, List<String> symbols, int bet, int win,
                             boolean freeGame, int freeGameRound) {
        this.userId = userId;
        this.symbols = symbols;
        this.bet = bet;
        this.win = win;
        this.freeGame = freeGame;
        this.freeGameRound = freeGameRound;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public List<String> getSymbols() { return symbols; }
    public void setSymbols(List<String> symbols) { this.symbols = symbols; }

    public int getBet() { return bet; }
    public void setBet(int bet) { this.bet = bet; }

    public int getWin() { return win; }
    public void setWin(int win) { this.win = win; }

    public boolean isFreeGame() { return freeGame; }
    public void setFreeGame(boolean freeGame) { this.freeGame = freeGame; }

    public int getFreeGameRound() { return freeGameRound; }
    public void setFreeGameRound(int freeGameRound) { this.freeGameRound = freeGameRound; }
}
