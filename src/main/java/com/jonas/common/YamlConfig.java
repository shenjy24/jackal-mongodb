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
    private MongodbConfig mongodb;

    @Data
    public static class MongodbConfig {
        private String database;
        private String address;
    }
}
