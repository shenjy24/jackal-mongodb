package com.jonas;

import com.jonas.data.Game;
import com.jonas.data.GameManager;
import com.jonas.data.bean.Player;
import com.jonas.data.bean.Team;
import com.jonas.util.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        String gameId = "b7852900-4d13-4923-922a-770af31d10a8";
        gameManager.upset(buildGame(gameId));
    }

    @Test
    public void testInsert() {
        String gameId = UUID.randomUUID().toString();
        gameManager.insert(buildGame(gameId));
    }

    @Test
    public void testInsertMany() {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String gameId = UUID.randomUUID().toString();
            games.add(buildGame(gameId));
        }
        gameManager.insert(games);
    }

    @Test
    public void testFind() {
        Game game = gameManager.findOne("f29f3ad3-6015-4d61-9b47-4f3cf62c9392");
        System.out.println(game);
    }

    @Test
    public void testFindAll() {
        List<Game> games = gameManager.find();
        System.out.println(games);
    }

    @Test
    public void testUpdate() {
        gameManager.update("b7852900-4d13-4923-922a-770af31d10a8", "团战模式");
    }

    @Test
    public void testQuery() {
        List<Game> games = gameManager.queryByPlayer("e9e01cd8-9961-4834-8396-e2f9455f868b", 1599283132778L, 20);
        System.out.println(games);
    }

    public Game buildGame(String uuid) {
        Game game = new Game();
        game.setGameId(uuid);
        game.setGameType("组队模式");
        game.setGameMap("峡谷之颠");
        game.setWinTeam("红队");
        game.setBeginTime(System.currentTimeMillis());
        game.setEndTime(game.getBeginTime() + 3600000);

        Team team1 = buildTeam("红队", true);
        Team team2 = buildTeam("蓝队", false);
        List<Team> teams = Arrays.asList(team1, team2);
        game.setTeams(teams);
        return game;
    }

    private Team buildTeam(String name, boolean win) {
        Team team = new Team();
        team.setName(name);
        team.setWin(win);
        Player player1 = buildPlayer(win);
        Player player2 = buildPlayer(win);
        List<Player> players = Arrays.asList(player1, player2);
        team.setPlayers(players);
        return team;
    }

    private Player buildPlayer(boolean win) {
        String uuid = UUID.randomUUID().toString();
        Player player = new Player();
        player.setPlayerId(uuid);
        player.setPlayerName(uuid);
        player.setPoints(RandomUtils.nextInt(1, 100));
        player.setWin(win);
        return player;
    }
}
