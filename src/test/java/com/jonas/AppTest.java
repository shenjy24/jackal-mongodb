package com.jonas;

import com.jonas.data.Game;
import com.jonas.data.GameManager;
import com.jonas.data.bean.Player;
import com.jonas.data.bean.Team;
import com.jonas.util.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private GameManager gameManager;

    @Before
    public void init() {
        gameManager = new GameManager();
    }

    @Test
    public void testUpset() {
        String gameId = UUID.randomUUID().toString();
        gameManager.upset(buildGame(gameId));
    }

    @Test
    public void testFind() {
        System.out.println(gameManager.find("228ae8fb-75ae-4558-a92f-ea795e8d7363"));
    }

    @Test
    public void testUpdate() {
        gameManager.update("228ae8fb-75ae-4558-a92f-ea795e8d7363", "团战模式");
    }

    @Test
    public void testQuery() {
        List<Game> games = gameManager.queryByPlayer("e9e01cd8-9961-4834-8396-e2f9455f868b", 1599283132778L, 20);
        System.out.println(games);
    }

    public Game buildGame(String uuid) {
        Game game = Game.builder().gameId(uuid)
                .gameType("组队模式").gameMap("峡谷之颠")
                .winTeam("红队")
                .beginTime(System.currentTimeMillis())
                .endTime(System.currentTimeMillis() + 3600000)
                .build();

        Team team1 = buildTeam("红队", true);
        Team team2 = buildTeam("蓝队", false);
        List<Team> teams = Arrays.asList(team1, team2);
        game.setTeams(teams);
        return game;
    }

    private Team buildTeam(String name, boolean win) {
        Team team = Team.builder().name(name).win(win).build();
        Player player1 = buildPlayer(win);
        Player player2 = buildPlayer(win);
        List<Player> players = Arrays.asList(player1, player2);
        team.setPlayers(players);
        return team;
    }

    private Player buildPlayer(boolean win) {
        String uuid = UUID.randomUUID().toString();
        Player player = Player.builder()
                .playerId(uuid)
                .playerName(uuid)
                .points(RandomUtils.nextInt(1, 100))
                .win(win)
                .build();
        return player;
    }
}
