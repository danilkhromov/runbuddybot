package org.runbuddy.database;

import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danil Khromov.
 */
public class DBManager {
    private static final String DEFAULT_CONNECTION_URL = "jdbc:sqlite:runbuddybot.db";
    private final String connectionUrl;

    public DBManager() {
        this(DEFAULT_CONNECTION_URL);
    }

    DBManager(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void createTables() {
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            String query = CreationQueries.CREATE_SHOES_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_WEIGHT_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_ARCH_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_TYPE_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_ROAD_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_USERS_TABLE;
            statement.executeUpdate(query);

            query = CreationQueries.CREATE_TEMP_TABLE;
            statement.executeUpdate(query);
        } catch (SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addUser(String userId) {
        String insert = new QueryBuilder()
                .insertInto("users", "user_id")
                .values(userId)
                .create();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(insert);
        } catch (SQLiteException e) {
            if (e.getResultCode().equals(SQLiteErrorCode.SQLITE_CONSTRAINT_PRIMARYKEY)) {
                System.out.println("Record already exists");
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void addTempResult(Connection connection, String userId, String model, String name, String photoUrl, String url) throws SQLException {
        String insert = new QueryBuilder()
                .insertInto("temp", "user_id", "model", "name", "photo_url", "url", "timestamp")
                .values(userId, model, name, photoUrl, url, "datetime('now')")
                .create();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void getResult(String userId, String result) {
        String[] conditions = result.split(",");
        String select = new QueryBuilder()
                .select("shoes.model", "name", "photo_url", "url")
                .from("shoes")
                .innerJoin("weight").on("weight.model = shoes.model")
                .innerJoin("arch").on("arch.model = shoes.model")
                .innerJoin("type").on("type.model = shoes.model")
                .innerJoin("road").on("road.model = shoes.model")
                .where(conditions[0])
                .and(conditions[1])
                .and(conditions[2])
                .and(conditions[3])
                .and(conditions[4])
                .orderBy("RANDOM()")
                .create();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(select)) {

            while (resultSet.next()) {
                String model = "'" + resultSet.getString("model") + "'";
                String name = "'" + resultSet.getString("name") + "'";
                String photoUrl = "'" + resultSet.getString("photo_url") + "'";
                String url = "'" + resultSet.getString("url") + "'";

                addTempResult(connection, userId, model, name, photoUrl, url);
            }
        } catch (SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static synchronized String[] getShoe(String userId) {
        String[] shoe = new String[3];
        String query = new QueryBuilder()
                .select("model", "name", "photo_url", "url")
                .from("temp")
                .where("user_id").eq(userId)
                .limit(1)
                .create();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String model = "'" + resultSet.getString("model") + "'";
                shoe[0] = resultSet.getString("name");
                shoe[1] = resultSet.getString("photo_url");
                shoe[2] = resultSet.getString("url");

                query = new QueryBuilder()
                        .deleteFrom("temp")
                        .where("user_id").eq(userId)
                        .and("model").eq(model)
                        .create();
                statement.executeUpdate(query);
            }
        } catch (SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
        return shoe;
    }

    void cleanTempTable() {
        String query = new QueryBuilder()
                .deleteFrom("temp")
                .where("timestamp >= datetime('now', '-3 hours')")
                .create();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ) {
          statement.executeUpdate(query);
        } catch (SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public List<Long> getUsers() {
        List<Long> users = new ArrayList<>();
        String query = new QueryBuilder()
                .select("user_id")
                .from("users")
                .create();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                users.add(resultSet.getLong("user_id"));
            }
        } catch (SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
        return users;
    }
}
