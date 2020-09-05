package com.jonas.data;

import com.jonas.data.bean.Team;
import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

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
@Data
@Builder
public class Game {

    public static final String COLLECTION = "game";

    private String gameId;
    private String gameType;
    private String gameMap;
    private List<Team> teams;
    private String winTeam;
    private long beginTime;
    private long endTime;
}
