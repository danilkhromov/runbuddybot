package org.runbuddy.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniil Khromov.
 */
public class DBManager implements Manager {

    private List <String[]> shoes;
    private List<String> users;
    private Map<String, String> temp;

    public DBManager() {
        shoes = new ArrayList<>();
        users = new ArrayList<>();
        temp = new HashMap<>();
        createTables();
    }

    @Override
    public void createTables() {
        for (int i = 1; i < 11; i++) {
            String[] shoe = new String[3];
            shoe[0] = "name" + i;
            shoe[1] = "image" + i;
            shoe[2] = "url" + i;
            shoes.add(shoe);
        }
    }

    @Override
    public void addUser(String userId) {
        users.add(userId);
    }

    @Override
    public String[] getShoe(String userId, String result) {
        return shoes.get(1);
    }

    @Override
    public void deleteViewedShoes(String userId) {

    }

    @Override
    public void cleanTempTable() {

    }

    @Override
    public List<Long> getUsers() {
        return null;
    }
}
