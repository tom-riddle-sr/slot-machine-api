package com.example.demo.entity;

import com.example.demo.converter.StringListConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Comment("玩家旋轉紀錄")
@Table(name = "spinRecord", indexes = {
        @Index(name = "idx_user_id", columnList = "userId")})
public class SpinRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int userId;

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false)
    @Comment("開出的盤面")
    private List<String> symbols;

    @Column(nullable = false)
    @Comment("押注金額")
    private int bet;

    @Column(nullable = false)
    @Comment("贏得金額")
    private int win;

    @Column(nullable = false)
    @Comment("是否進入freeGame")
    private boolean isFreeGame;

    @Column(nullable = false)
    @Comment("Free Game 第幾輪，0 代表主遊戲")
    private int freeGameRound;

    @Column(nullable = false, updatable = false)
    @Comment("旋轉時間")
    private LocalDateTime createdAt;

    public SpinRecord() {}

    public SpinRecord(int userId, List<String> symbols, int bet, int win,
                      boolean isFreeGame, int freeGameRound) {
        this.userId = userId;
        this.symbols = symbols;
        this.bet = bet;
        this.win = win;
        this.isFreeGame = isFreeGame;
        this.freeGameRound = freeGameRound;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public List<String> getSymbols() { return symbols; }
    public int getBet() { return bet; }
    public int getWin() { return win; }
    public boolean isFreeGame() { return isFreeGame; }
    public int getFreeGameRound() { return freeGameRound; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setSymbols(List<String> symbols) { this.symbols = symbols; }
    public void setBet(int bet) { this.bet = bet; }
    public void setWin(int win) { this.win = win; }
    public void setFreeGame(boolean isFreeGame) { this.isFreeGame = isFreeGame; }
    public void setFreeGameRound(int freeGameRound) { this.freeGameRound = freeGameRound; }
}
