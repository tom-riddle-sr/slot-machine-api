package com.example.demo.dto;

import java.util.List;

// 查歷史紀錄的回應：某玩家 + 他的旋轉紀錄清單（精簡版）
public class HistoryRes {

    private Integer userId;
    private List<SpinRecordDto> records;

    public HistoryRes() {
    }

    public HistoryRes(Integer userId, List<SpinRecordDto> records) {
        this.userId = userId;
        this.records = records;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<SpinRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<SpinRecordDto> records) {
        this.records = records;
    }
}
