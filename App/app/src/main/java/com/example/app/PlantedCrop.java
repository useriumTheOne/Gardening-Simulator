package com.example.app;

import java.io.Serializable;

public class PlantedCrop  implements Serializable {
    private Crop crop;
    private long plantedAt;
    public PlantedCrop(Crop crop) {
        this.crop = crop;
        this.plantedAt = System.currentTimeMillis();
    }

    public Crop getCrop() { return crop; }

    public long getElapsedSeconds()
    {

        long e = (System.currentTimeMillis() - plantedAt)/ 1000;
        return e;
    }

    public boolean isGrown() {
        return getElapsedSeconds() >= crop.getGrowthTime();
    }
}
