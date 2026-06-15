package com.example.demo.game;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WinCalculator {

    //   0 1 2
    //   3 4 5
    //   6 7 8
    private static final int[][] LINES = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // 3 橫
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // 3 直
            {0, 4, 8}, {2, 4, 6}             // 2 斜
    };

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
        return target.getMultiplier() * bet;
    }
}
