package org.runbuddy.database;

import java.util.List;

/**
 * Created by Daniil Khromov.
 */
interface Manager {

    void createTables();

    void addUser(String userId);

    String[] getShoe(String userId, String result);

    void deleteViewedShoes(String userId);

    void cleanTempTable();

    List<Long> getUsers();
}