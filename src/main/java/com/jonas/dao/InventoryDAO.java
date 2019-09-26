package com.jonas.dao;

import com.alibaba.fastjson.JSON;
import com.jonas.document.Inventory;
import com.jonas.dto.InventorySize;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;



/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-09-26
 */
public class InventoryDAO {
    private MongoClient mongoClient;
    private MongoCollection<Document> collection;
    private String databaseName = "retail";
    private String collectionName = "inventory";

    public InventoryDAO(String host, int port) {
        this.mongoClient = new MongoClient(host, port);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
        collection = mongoDatabase.getCollection(collectionName);
    }

    public void closeClient() {
        mongoClient.close();
    }

    public void insertOne(Inventory inventory) {
        collection.insertOne(Document.parse(JSON.toJSONString(inventory)));
    }

    public void insertMany(List<Inventory> inventories) {
        List<Document> documents = new ArrayList<>();
        for (Inventory inventory : inventories) {
            documents.add(Document.parse(JSON.toJSONString(inventory)));
        }
        collection.insertMany(documents);
    }

    public void updateByItem(String item, String uom) {
        collection.updateMany(Filters.eq("item", item),
                Updates.combine(Updates.set("size.uom", uom), Updates.currentDate("lastModified")));
    }

    public List<Inventory> findAll() {
        FindIterable<Document> documents = collection.find();
        return convertInventories(documents);
    }

    public List<Inventory> findByStatus(String status) {
        FindIterable<Document> documents = collection.find(Filters.eq("status", status));
        return convertInventories(documents);
    }

    public List<Inventory> findBySize(InventorySize inventorySize) {
        FindIterable<Document> documents = collection.find(Filters.eq("size", Document.parse(JSON.toJSONString(inventorySize))));
        return convertInventories(documents);
    }

    private List<Inventory> convertInventories(FindIterable<Document> documents) {
        List<Inventory> inventories = new ArrayList<>();
        for (Document document : documents) {
            inventories.add(JSON.parseObject(document.toJson(), Inventory.class));
        }
        return inventories;
    }
}
