package com.example.app;

import java.io.Serializable;
import java.util.Objects;

public class Item  implements Serializable {
    private String name;
    private double buyPrice;
    private double sellPrice;

    public Item(String name, double buyPrice, double sellPrice)
    {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getName() { return name; }
    public double getBuyPrice() { return buyPrice; }
    public double getSellPrice() { return sellPrice; }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() //FOR THE MAP!!!!!
    {
        return Objects.hash(name);
    }
}
