package com.jonas.data;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        MongoTemplate.insertOne(collection, game);
    }

    public void insert(List<Game> games) {
        MongoTemplate.insertMany(collection, games);
    }

    public void update(String gameId, String gameType) {
        Map<String, Object> args = new HashMap<>();
        args.put("gameType", gameType);
        args.put("endTime", System.currentTimeMillis());
        MongoTemplate.update(collection, Filters.eq("gameId", gameId), args);
    }

    public void upset(Game game) {
        Bson filter = Filters.eq("gameId", game.getGameId());
        MongoTemplate.upset(collection, filter, game);
    }

    public List<Game> find() {
        FindIterable<Document> documents = collection.find();
        return MongoTemplate.serialize(Game.class, documents);
    }

    public Game findOne(String gameId) {
        Bson bson = Filters.eq("gameId", gameId);
        return MongoTemplate.findOne(collection, bson, Game.class);
    }

    public List<Game> findByPlayer(String playerId) {
        Bson bson = Filters.eq("teams.players.playerId", playerId);
        return MongoTemplate.findAll(collection, bson, Game.class);
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
        return MongoTemplate.serialize(Game.class, documents);
    }
}
