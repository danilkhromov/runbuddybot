package org.runbuddy.dbmanagement;

/**
 * Created by Danil Khromov.
 */
public class SelectBuilder {
    private String query;

    SelectBuilder(String conditions) {
        query = "SELECT " + conditions;
    }

    public SelectBuilder from(String condition) {
        query = "FROM " + condition;
        return this;
    }

    public SelectBuilder where(String condition) {
        query = "WHERE " + condition;
        return this;
    }

    public SelectBuilder orderBy(String condition) {
        query = "ORDER BY " + condition;
        return this;
    }

    public SelectBuilder innerJoin(String condition) {
        query = "INNER JOIN " + condition;
        return this;
    }

    public String getQuery() {
        return query;
    }
}
