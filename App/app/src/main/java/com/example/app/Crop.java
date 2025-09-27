package com.example.app;

public class Crop
{
    private String type;
    private int buyPrice;
    private int sellPrice;
    private int growthTime; // in seceonds
    public Crop(String type, int buyPrice, int sellPrice, int growthTime)
    {
        this.type = type;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.growthTime = growthTime;
    }
    public String getType()
    {
        return type;
    }
    public int getBuyPrice()
    {
        return buyPrice;
    }
    public int getSellPrice()
    {
        return sellPrice;
    }
    public int getGrowthTime()
    {
        return growthTime;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public void setBuyPrice(int buyPrice)
    {
        this.buyPrice = buyPrice;
    }
    public void setSellPrice(int sellPrice)
    {
        this.sellPrice = sellPrice;
    }

}
