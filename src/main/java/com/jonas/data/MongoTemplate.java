package com.jonas.data;

import com.jonas.util.CollectionUtils;
import com.jonas.util.JacksonUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shenjy
 * @date 2020/12/31
 * @description
 */
public class MongoTemplate {

    public static void createIndex(MongoCollection<Document> collection, boolean asc, String...fields) {
        if (asc) {
            collection.createIndex(Indexes.ascending(fields));
        } else {
            collection.createIndex(Indexes.descending(fields));
        }
    }

    public static <T> T findOne(MongoCollection<Document> collection, Bson filter, Class<T> clazz) {
        FindIterable<Document> documents = collection.find(filter);
        List<T> list = serialize(clazz, documents);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    public static <T> List<T> findAll(MongoCollection<Document> collection, Bson filter, Class<T> clazz) {
        FindIterable<Document> documents = collection.find(filter);
        return serialize(clazz, documents);
    }

    public static <T> void insertOne(MongoCollection<Document> collection, T t) {
        collection.insertOne(Document.parse(JacksonUtils.toJson(t)));
    }

    public static <T> void insertMany(MongoCollection<Document> collection, List<T> list) {
        List<Document> documents = new ArrayList<>();
        for (T t : list) {
            documents.add(Document.parse(JacksonUtils.toJson(t)));
        }
        collection.insertMany(documents);
    }

    public static <T> void upset(MongoCollection<Document> collection, Bson filter, T t) {
        Document document = Document.parse(JacksonUtils.toJson(t));
        collection.replaceOne(filter, document, new UpdateOptions().upsert(true));
    }

    public static void update(MongoCollection<Document> collection, Bson filter,  Map<String, Object> updates) {
        List<Bson> bsons = new ArrayList<>(updates.size());
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            Bson bson = Updates.set(entry.getKey(), entry.getValue());
            bsons.add(bson);
        }
        collection.updateMany(filter, Updates.combine(bsons));
    }

    public static <T> List<T> serialize(Class<T> clz, FindIterable<Document> documents) {
        JsonWriterSettings settings = JsonWriterSettings.builder()
                .int64Converter((value, writer) -> writer.writeNumber(value.toString()))
                .build();

        List<T> list = new ArrayList<>();
        for (Document document : documents) {
            list.add(JacksonUtils.toBean(document.toJson(settings), clz));
        }
        return list;
    }
}
