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

    public void createTables() {
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            String query = CreationQueries.CREATE_SHOES_TABLE;
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

    public static synchronized String[] getShoe(String userId, String result) {
        String[] shoe = new String[3];
        String[] conditions = result.split(",");
        String query = new QueryBuilder()
                .select("model", "name", "photo_url", "url")
                .from("shoes")
                .where(conditions[0])
                .and(conditions[1])
                .and(conditions[2])
                .and(conditions[3])
                .and(conditions[4])
                .orderBy("RANDOM()")
                .limit(1)
                .create();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                shoe[0] = resultSet.getString("name");
                shoe[1] = resultSet.getString("photo_url");
                shoe[2] = resultSet.getString("url");
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
