package com.example.app;

public class Seed extends Item {

    private Crop crop;
    public Seed(Crop crop)
    {
        super(crop.getType()+" Seed", crop.getBuyPrice(), crop.getSellPrice());
        this.crop = crop;
    }
    public Crop getCrop()
    {
        return crop;
    }
}
