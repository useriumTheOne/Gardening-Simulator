package com.example.app;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory  implements Serializable {

    private Map<Item, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(Item item, int quantity) {
        if (quantity <= 0) return;

        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public boolean removeItem(Item item, int quantity)
    {
        if (quantity <= 0) return false;

        Integer current = items.get(item);
        if (current == null || current < quantity) {
            return false;
        }

        int remaining = current - quantity;
        if (remaining > 0) {
            items.put(item, remaining);
        } else {
            items.remove(item);
        }
        return true;
    }

    public int getQuantity(Item item)
    {
        return items.getOrDefault(item, 0);
    }
    public boolean hasItem(Item item)
    {
        return items.containsKey(item);
    }
    public boolean hasItem(Item item, int quantity)
    {
        return getQuantity(item) >= quantity;
    }

    public Set<Item> getItems()
    {
        return Collections.unmodifiableSet(items.keySet());
    }
}
