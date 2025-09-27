package com.example.app;

public class PlantedCrop {
    private Crop crop;
    private long plantedAt;
    public PlantedCrop(Crop crop) {
        this.crop = crop;
        this.plantedAt = System.currentTimeMillis();
    }

    public Crop getCrop() { return crop; }

    public long getElapsedSeconds() {
        return (System.currentTimeMillis() - plantedAt) / 1000;
    }

    public boolean isGrown() {
        return getElapsedSeconds() >= crop.getGrowthTime();
    }
}
