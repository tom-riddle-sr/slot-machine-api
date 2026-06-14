package com.example.demo.game;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WinCalculator {

    // 8 條中獎線（格子編號 0~8）—— 這個資料直接用，不用重寫
    //   0 1 2
    //   3 4 5
    //   6 7 8
    private static final int[][] LINES = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // 3 橫
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // 3 直
            {0, 4, 8}, {2, 4, 6}             // 2 斜
    };

    public int calculate(List<SymbolType> board, int bet) {
        // 步驟 1: 宣告一個 win = 0，用來累加總贏錢

        // 步驟 2: 用 for 迴圈跑過 LINES 的每一條線（每個 line 是 int[]，3 個格子編號）

        //   步驟 2-1: 用 line[0]/line[1]/line[2] 去 board.get(...) 撈出這條線的 3 個符號

        //   步驟 2-2: 把這 3 個符號丟給 lineWin(...) 判斷這條線贏多少，加進 win

        // 步驟 3: 回傳 win

        return 0; // ← 先讓它能編譯，寫完記得改
    }

    // 判斷「一條線」(3 格) 贏多少。建議自己寫一個 private 方法
    private int lineWin(SymbolType a, SymbolType b, SymbolType c, int bet) {
        // 步驟 A: 如果 3 個都是 Wild → return bet * 100

        // 步驟 B: 找出「非 Wild」的符號，檢查是不是全部同一種普通符號
        //   - 用一個變數（例如 target）記住「第一個遇到的普通符號」
        //   - 跑過 a、b、c：
        //       * 是 Wild → 跳過（continue）
        //       * 是 Scatter → return 0（Scatter 不連線）
        //       * target 還沒設 → 設成這個符號
        //       * target 已設但跟現在這個不一樣 → return 0（符號不一致，不中）

        // 步驟 C: 跑完都沒 return 0，代表非 Wild 全是同一種 target（Wild 補位）
        //   → return bet * target.getMultiplier()

        return 0; // ← 先讓它能編譯，寫完記得改
    }
}


/* ===== 原本寫好的版本（卡住可參考，完成後刪掉這整段）=====

public int calculate(List<SymbolType> board, int bet) {
    int win = 0;
    for (int[] line : LINES) {
        SymbolType a = board.get(line[0]);
        SymbolType b = board.get(line[1]);
        SymbolType c = board.get(line[2]);
        win += lineWin(a, b, c, bet);
    }
    return win;
}

private int lineWin(SymbolType a, SymbolType b, SymbolType c, int bet) {
    if (a.isWild() && b.isWild() && c.isWild()) {
        return bet * 100;
    }
    SymbolType target = null;
    for (SymbolType s : new SymbolType[]{a, b, c}) {
        if (s.isWild()) {
            continue;
        }
        if (s.isScatter()) {
            return 0;
        }
        if (target == null) {
            target = s;
        } else if (target != s) {
            return 0;
        }
    }
    return bet * target.getMultiplier();
}

===== 參考結束 ===== */
