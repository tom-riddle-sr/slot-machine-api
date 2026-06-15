package com.example.demo.game;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// 依「機率權重」隨機產生符號
@Component
public class SymbolGenerator {

    private final Random random = new Random();

    // 所有符號權重的總和（30+25+20+...=100）
    private static final int TOTAL_WEIGHT;
    static {
        int sum = 0;
        for (SymbolType s : SymbolType.values()) {
            sum += s.getProbability();
        }
        TOTAL_WEIGHT = sum;
    }

    // 產生一個 9 格的盤面
    public List<SymbolType> spin() {
        List<SymbolType> board = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            board.add(pickOne());

        }
        return board;
    }

    // 加權隨機抽一個符號
    private SymbolType pickOne() {
        int roll = random.nextInt(TOTAL_WEIGHT); // 0 ~ 99

        int cumulative = 0;
        for (SymbolType s : SymbolType.values()) {
            cumulative += s.getProbability();
            if (roll < cumulative) {
                return s;
            }
        }
        // 理論上不會走到這（為了讓編譯器安心）
        return SymbolType.CHERRY;
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


