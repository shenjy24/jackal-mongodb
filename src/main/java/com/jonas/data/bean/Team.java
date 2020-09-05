package com.jonas.data.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-09-26
 */
@Data
@Builder
public class Team {
    private String name;
    private boolean win;
    private List<Player> players;
}
