package game;

import game.enums.ItemProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Item implements Serializable {
    protected String name;
    protected Map<ItemProperty, Action> itemProperties;

    public Item(String name) {
        this.name = name;
        itemProperties = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<ItemProperty, Action> getItemProperties() {
        return itemProperties;
    }

    public Item addItemProperty(ItemProperty itemProperty, Action action) {
        this.itemProperties.put(itemProperty, action);
        return this;
    }
}
