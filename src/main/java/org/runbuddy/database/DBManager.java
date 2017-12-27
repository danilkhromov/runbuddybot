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
public class DBManager implements Manager{

    private static volatile DBManager dbManager;

    private DBManager() {}

    public static DBManager getInstance() {
        if (dbManager == null) {
            synchronized (DBManager.class) {
                if (dbManager == null) {
                    dbManager = new DBManager();
                }
            }
        }
        return dbManager;
    }

    @Override
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

    /**
     * Adds new user to "users" table in db
     *
     * @param userId id of User that send "/start" command
     */
    @Override
    public synchronized void addUser(String userId) {
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

    /**
     * Selects random shoe from "shoes" table after completion of quiz depending on user's answers.
     * Adds selected shoes to "temp" table to prevent duplicate selection.
     *
     * @param userId id of telegram user that passed the quiz
     * @param result results of a test in string array
     * @return       String that contains shoe name, image url and product url
     */
    @Override
    public synchronized String[] getShoe(String userId, String result) {
        String[] shoe = new String[3];
        String[] conditions = result.split(",");
        String subQuery = new QueryBuilder()
                .select("model")
                .from("temp")
                .where("user_id").eq(userId)
                .create();
        String query = new QueryBuilder()
                .select("model", "photo_url", "url")
                .from("shoes")
                .where(conditions[0])
                .and(conditions[1])
                .and(conditions[2])
                .and(conditions[3])
                .and(conditions[4])
                .and("model").notIn(" (" + subQuery + ")")
                .orderBy("RANDOM()")
                .limit(1)
                .create();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                shoe[0] = "'" + resultSet.getString("model") + "'";
                shoe[1] = resultSet.getString("photo_url");
                shoe[2] = resultSet.getString("url");

                String insert = new QueryBuilder()
                        .insertInto("temp", "user_id", "model")
                        .values(userId, shoe[0])
                        .create();
                statement.executeUpdate(insert);
            }
        } catch (SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
        return shoe;
    }

    /**
     * Deletes all viewed shoes from "temp" table if user decides to run quiz again
     *
     * @param userId id of telegram user that passed the quiz
     */
    @Override
    public synchronized void deleteViewedShoes(String userId) {
        String delete = new QueryBuilder()
                .deleteFrom("temp")
                .where("user_id").eq(userId)
                .create();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(delete);
        } catch (SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes all shoes from "temp" table.
     */
    @Override
    public synchronized void cleanTempTable() {
        String query = new QueryBuilder()
                .deleteFrom("temp")
                .where("timestamp < datetime('now')")
                .create();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ) {
          statement.executeUpdate(query);
        } catch (SQLException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    @Override
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
