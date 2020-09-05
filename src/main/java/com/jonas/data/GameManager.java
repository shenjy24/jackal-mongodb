package com.jonas.data;

import com.jonas.util.GsonUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-09-26
 */
public class GameManager {
    private MongoCollection<Document> collection;

    public GameManager() {
        collection = DataManager.getCollection(Game.COLLECTION);
        collection.createIndex(Indexes.ascending("gameId"));
        collection.createIndex(Indexes.ascending("teams.players.playerId"));
    }

    public void insert(Game game) {
        collection.insertOne(Document.parse(GsonUtils.toJson(game)));
    }

    public void insert(List<Game> games) {
        List<Document> documents = new ArrayList<>();
        for (Game game : games) {
            documents.add(Document.parse(GsonUtils.toJson(game)));
        }
        collection.insertMany(documents);
    }

    public void update(String gameId, String gameType) {
        collection.updateMany(Filters.eq("gameId", gameId),
                Updates.combine(Updates.set("gameType", gameType), Updates.currentDate("lastModified")));
    }

    public void upset(Game game) {
        Bson filter = Filters.eq("gameId", game.getGameId());
        Document document = Document.parse(GsonUtils.toJson(game));
        collection.replaceOne(filter, document, new ReplaceOptions().upsert(true));
    }

    public List<Game> find() {
        FindIterable<Document> documents = collection.find();
        return DataManager.convert(documents, Game.class);
    }

    public List<Game> find(String gameId) {
        FindIterable<Document> documents = collection.find(Filters.eq("gameId", gameId));
        return DataManager.convert(documents, Game.class);
    }

    public List<Game> findByPlayer(String playerId) {
        FindIterable<Document> documents = collection.find(Filters.eq("teams.players.playerId", playerId));
        return DataManager.convert(documents, Game.class);
    }

    /**
     * 分页查询
     *
     * @param playerId 玩家ID
     * @param lastTime 上一页最后一条记录endTime
     * @param pageSize 每页条数
     * @return
     */
    public List<Game> queryByPlayer(String playerId, long lastTime, int pageSize) {
        Bson filter = Filters.and(Filters.eq("teams.players.playerId", playerId), Filters.lt("endTime", lastTime));
        FindIterable<Document> documents = collection.find(filter).sort(Sorts.descending("endTime")).limit(pageSize);
        return DataManager.convert(documents, Game.class);
    }
}
