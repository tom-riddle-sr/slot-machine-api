package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "symbol")
public class Symbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int probability;

    @Column(nullable = false)
    private int multiplier;

    @Column(nullable = false)
    private boolean isWild;

    @Column(nullable = false)
    private boolean isScatter;

    public Symbol() {}

    public Symbol(String name, int probability, int multiplier, boolean isWild, boolean isScatter) {
        this.name = name;
        this.probability = probability;
        this.multiplier = multiplier;
        this.isWild = isWild;
        this.isScatter = isScatter;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getProbability() { return probability; }
    public int getMultiplier() { return multiplier; }
    public boolean isWild() { return isWild; }
    public boolean isScatter() { return isScatter; }

    public void setName(String name) { this.name = name; }
    public void setProbability(int probability) { this.probability = probability; }
    public void setMultiplier(int multiplier) { this.multiplier = multiplier; }
    public void setWild(boolean isWild) { this.isWild = isWild; }
    public void setScatter(boolean isScatter) { this.isScatter = isScatter; }
}
