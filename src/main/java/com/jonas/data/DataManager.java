package com.jonas.data;

import com.jonas.common.YamlConfig;
import com.jonas.util.GsonUtils;
import com.jonas.util.YamlParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * DataManager
 *
 * @author shenjy
 * @version 1.0
 * @date 2020-09-05
 */
public class DataManager {
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    private DataManager(){}

    static {
        YamlConfig yamlConfig = YamlParser.toBean("config.yml", YamlConfig.class);
        mongoClient = new MongoClient(yamlConfig.getMongo().getIp(), yamlConfig.getMongo().getPort());
        mongoDatabase = mongoClient.getDatabase(yamlConfig.getMongo().getDatabase());
    }

    public static MongoCollection<Document> getCollection(String collection) {
        return mongoDatabase.getCollection(collection);
    }

    public static <T> List<T> convert(FindIterable<Document> documents, Class<T> clz) {
        JsonWriterSettings settings = JsonWriterSettings.builder()
                .int64Converter((value, writer) -> writer.writeNumber(value.toString()))
                .build();

        List<T> list = new ArrayList<>();
        for (Document document : documents) {
            list.add(GsonUtils.toBean(document.toJson(settings), clz));
        }
        return list;
    }
}
