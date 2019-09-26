package com.jonas;

import com.jonas.dao.InventoryDAO;
import com.jonas.document.Inventory;
import com.jonas.dto.InventorySize;

import java.util.Collections;

import static java.util.Arrays.asList;

/**
 * Hello world!
 */
public class App {
    private static InventoryDAO inventoryDAO = new InventoryDAO("127.0.0.1", 27017);

    public static void main(String[] args) {
        inventoryDAO.updateByItem("paper", "dm");
    }

    private static void insert() {
        Inventory inventory = new Inventory();
        inventory.setItem("ruler");
        inventory.setQty(10);
        inventory.setStatus("B");
        inventory.setSize(new InventorySize(10, 10, "cm"));
        inventory.setTags(asList("yellow"));

        inventoryDAO.insertMany(Collections.singletonList(inventory));
    }
}
