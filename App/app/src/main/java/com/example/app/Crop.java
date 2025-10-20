package com.example.app;

import java.io.Serializable;

public class Crop implements Serializable {
    private String type;
    private double buyPrice;
    private double sellPrice;
    private double growthTime;

    public Crop() {}

    public Crop(String type, double buyPrice, double sellPrice, double growthTime) {
        this.type = type;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.growthTime = growthTime;
    }

    public String getType() { return type; }
    public double getBuyPrice() { return buyPrice; }
    public double getSellPrice() { return sellPrice; }
    public double getGrowthTime() { return growthTime; }

    public void setType(String type) { this.type = type; }
    public void setBuyPrice(double buyPrice) { this.buyPrice = buyPrice; }
    public void setSellPrice(double sellPrice) { this.sellPrice = sellPrice; }
    public void setGrowthTime(double growthTime) { this.growthTime = growthTime; }
}
