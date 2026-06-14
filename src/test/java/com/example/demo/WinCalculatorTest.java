package com.example.demo;

import com.example.demo.game.SymbolType;
import com.example.demo.game.WinCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.game.SymbolType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// 線連線版的單元測試。盤面位置：
//   0 1 2
//   3 4 5
//   6 7 8
class WinCalculatorTest {

    private final WinCalculator calc = new WinCalculator();

    private List<SymbolType> board(SymbolType... s) {
        return List.of(s);
    }

    @Test
    void 中間橫線三櫻桃_中2x() {
        var b = board(
                LEMON, ORANGE, STAR,
                CHERRY, CHERRY, CHERRY,
                LEMON, ORANGE, DIAMOND);
        // 只有中間橫線 [3,4,5] = 3 櫻桃 → 100 × 2 = 200
        assertEquals(200, calc.calculate(b, 100));
    }

    @Test
    void 沒有任何線_回0() {
        var b = board(
                CHERRY, LEMON, ORANGE,
                STAR, DIAMOND, CHERRY,
                LEMON, ORANGE, STAR);
        // 每條線都沒湊齊同符號
        assertEquals(0, calc.calculate(b, 100));
    }

    @Test
    void wild補位_中間橫線2櫻桃加1wild() {
        var b = board(
                LEMON, ORANGE, STAR,
                CHERRY, CHERRY, WILD,
                LEMON, ORANGE, DIAMOND);
        // 中間橫線 [CHERRY,CHERRY,WILD] → wild 補成 3 櫻桃 → 200
        assertEquals(200, calc.calculate(b, 100));
    }

    @Test
    void 一條線三個wild_中100x() {
        var b = board(
                LEMON, ORANGE, DIAMOND,
                WILD, WILD, WILD,
                ORANGE, LEMON, STAR);
        // 中間橫線 3 wild → 100x = 10000，其他線無同符號
        assertEquals(10000, calc.calculate(b, 100));
    }

    @Test
    void 多條線_各自加總() {
        var b = board(
                CHERRY, CHERRY, CHERRY,
                ORANGE, STAR, DIAMOND,
                LEMON, LEMON, LEMON);
        // 上橫線 3 櫻桃 → 200；下橫線 3 檸檬 → 500；共 700
        assertEquals(700, calc.calculate(b, 100));
    }

    @Test
    void 斜線三鑽石_中50x() {
        var b = board(
                DIAMOND, LEMON, ORANGE,
                STAR, DIAMOND, CHERRY,
                ORANGE, LEMON, DIAMOND);
        // 斜線 [0,4,8] = 3 鑽石 → 100 × 50 = 5000
        assertEquals(5000, calc.calculate(b, 100));
    }
}
