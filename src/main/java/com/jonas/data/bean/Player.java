package com.jonas.data.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Player
 *
 * @author shenjy
 * @version 1.0
 * @date 2020-09-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String playerId;
    private String playerName;
    private int points;
    private boolean win;
}
