package com.example.app;

import java.io.Serializable;

public class Garden  implements Serializable {

    private static final int ROWS = 5;
    private static final int COLS = 5;

    private PlantedCrop[][] grid;
    public Garden()
    {
        grid = new PlantedCrop[ROWS][COLS];
    }

    public boolean plantCrop(int row, int col, Crop crop)
    {
        if (!isValidPosition(row, col) || grid[row][col] != null)
        {
            return false;
        }
        grid[row][col] = new PlantedCrop(crop);
        return true;
    }

    public PlantedCrop getPlantedCrop(int row, int col)
    {
        if (!isValidPosition(row, col))
        {
            return null;
        }
        return grid[row][col];
    }

    public boolean harvestCrop(int row, int col)
    {
        PlantedCrop pc = getPlantedCrop(row, col);
        if (pc != null && pc.isGrown())
        {
            grid[row][col] = null;
            return true;
        }
        return false;
    }

    public boolean plantCropFirstAvailable(Crop crop)
    {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == null) {
                    grid[r][c] = new PlantedCrop(crop);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidPosition(int row, int col)
    {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }
}
