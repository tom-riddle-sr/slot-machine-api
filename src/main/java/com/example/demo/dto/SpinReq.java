package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SpinReq {

    @NotNull(message = "userId不可為空")
    private Integer userId;
    @NotNull(message = "bet不可為空")
    @Min(1)
    private Integer bet;

    public SpinReq() {
    }

    public SpinReq(Integer userId, Integer bet) {
        this.userId = userId;
        this.bet = bet;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }
}
