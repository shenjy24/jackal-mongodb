package com.jonas.common;

import lombok.Data;

/**
 * YamlConfig
 *
 * @author shenjy
 * @version 1.0
 * @date 2020-09-05
 */
@Data
public class YamlConfig {
    private MongoConfig mongo;

    @Data
    public static class MongoConfig {
        private String ip;
        private Integer port;
        private String database;
    }
}
