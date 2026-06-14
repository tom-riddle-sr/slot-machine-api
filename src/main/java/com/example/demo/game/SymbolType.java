package com.example.demo.game;

// 7 種符號的設定（寫死在程式裡）
// enum 不只是一串常數，每個還可以「帶資料」：emoji、機率權重、倍數、是否 Wild/Scatter
public enum SymbolType {

    //         emoji  機率權重  倍數  isWild  isScatter
    CHERRY("🍒", 30, 2, false, false),
    LEMON("🍋", 25, 5, false, false),
    ORANGE("🍊", 20, 10, false, false),
    STAR("⭐", 12, 20, false, false),
    DIAMOND("💎", 6, 50, false, false),
    WILD("🃏", 4, 100, true, false),
    SCATTER("🎰", 3, 0, false, true);

    private final String emoji;
    private final int probability; // 機率權重（數字越大越常出現）
    private final int multiplier;  // 中獎倍數（三個一樣時 bet × 這個）
    private final boolean wild;
    private final boolean scatter;

    // enum 的建構子：上面每個常數後面的括號，就是在呼叫這個
    SymbolType(String emoji, int probability, int multiplier, boolean wild, boolean scatter) {
        this.emoji = emoji;
        this.probability = probability;
        this.multiplier = multiplier;
        this.wild = wild;
        this.scatter = scatter;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getProbability() {
        return probability;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public boolean isWild() {
        return wild;
    }

    public boolean isScatter() {
        return scatter;
    }
}
