package org.runbuddy.database;

/**
 * Created by Daniil Khromov.
 */
class CreationQueries {
    static final String CREATE_SHOES_TABLE = "CREATE TABLE IF NOT EXISTS shoes (" +
            "model integer PRIMARY KEY, " +
            "name text NOT NULL, " +
            "gender text NOT NULL, " +
            "photo_url text NOT NULL, " +
            "url text NOT NULL)";
    static final String CREATE_WEIGHT_TABLE = "CREATE TABLE IF NOT EXISTS weight (" +
            "model integer PRIMARY KEY, " +
            "more_than_80 boolean NOT NULL, " +
            "less_than_80 boolean NOT NULL, " +
            "FOREIGN KEY (model) REFERENCES shoes (model) " +
            "ON DELETE CASCADE ON UPDATE NO ACTION)";
    static final String CREATE_ARCH_TABLE = "CREATE TABLE IF NOT EXISTS arch (" +
            "model integer PRIMARY KEY, " +
            "high boolean NOT NULL, " +
            "medium boolean NOT NULL, " +
            "low boolean NOT NULL, " +
            "flat boolean NOT NULL, " +
            "FOREIGN KEY (model) REFERENCES shoes (model) " +
            "ON DELETE CASCADE ON UPDATE NO ACTION)";
    static final String CREATE_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS type (" +
            "model integer PRIMARY KEY NOT NULL, " +
            "speed boolean NOT NULL, " +
            "distance boolean NOT NULL, " +
            "FOREIGN KEY (model) REFERENCES shoes (model) " +
            "ON DELETE CASCADE ON UPDATE NO ACTION)";
    static final String CREATE_ROAD_TABLE = "CREATE TABLE IF NOT EXISTS road (" +
            "model integer PRIMARY KEY, " +
            "road boolean NOT NULL, " +
            "trail boolean NOT NULL, " +
            "FOREIGN KEY (model) REFERENCES shoes (model) " +
            "ON DELETE CASCADE ON UPDATE NO ACTION)";
    static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "user_id integer PRIMARY KEY)";
    static final String CREATE_TEMP_TABLE = "CREATE TABLE IF NOT EXISTS temp (" +
            "user_id integer NOT NULL, " +
            "model integer NOT NULL, " +
            "name text NOT NULL, " +
            "photo_url text NOT NULL, " +
            "url text NOT NULL, " +
            "timestamp text NOT NULL DEFAULT (datetime('now')), " +
            "PRIMARY KEY (user_id, model) " +
            "FOREIGN KEY (model) REFERENCES shoes (model) " +
            "ON DELETE CASCADE ON UPDATE NO ACTION)";
}
