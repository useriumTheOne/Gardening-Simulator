package com.example.app;

import java.io.Serializable;

public class Player  implements Serializable {

    // Singleton instance
    private static Player instance;

    private String name;
    private double money;
    private Inventory inventory;
    private Garden garden;

    private Player(String name) {
        this.name = name;
        this.money = 100;
        this.inventory = new Inventory();
        this.garden = new Garden();
    }
    public static Player getInstance(String name) {
        if (instance == null) {
            instance = new Player(name);
        }
        return instance;
    }

    public static Player getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Player not initialized yet. Call getInstance(name) first.");
        }
        return instance;
    }
    public static void setInstanceFromLoad(Player loadedPlayer)
    {
        instance = loadedPlayer;
    }

    public String getName() { return name; }
    public double getMoney() { return money; }
    public Inventory getInventory() { return inventory; }
    public Garden getGarden() { return garden; }

    public void addMoney(double amount) {
        money += amount;
    }

    public boolean spendMoney(double amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        }
        return false;
    }
    public boolean buyItem(Item item) {
        if (spendMoney(item.getBuyPrice())) {
            inventory.addItem(item, 1);
            return true;
        }
        return false;
    }
    public boolean plantSeed(int row, int col, Seed seed)
    {
        boolean planted = garden.plantCrop(row, col, seed.getCrop());
        if (planted) {
            inventory.removeItem(seed, 1);
            return true;
        }
        return false;
    }

    public boolean harvestCrop(int row, int col)
    {
        PlantedCrop pc = garden.getPlantedCrop(row, col);
        if (garden.harvestCrop(row, col))
        {
            addMoney(pc.getCrop().getSellPrice());
            return true;
        }
        return false;
    }
}
