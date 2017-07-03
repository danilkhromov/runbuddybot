package org.runbuddy.dbmanagement;

/**
 * Created by Danil Khromov.
 */
public class QueryBuilder {
    private String query;

    public QueryBuilder select(String query) {
        this.query = "SELCET " + query;
        return this;
    }

    public QueryBuilder from(String query) {
        this.query = "FROM " + query;
        return this;
    }

    public QueryBuilder where(String condition) {
        query = "WHERE " + condition;
        return this;
    }

    public QueryBuilder orderBy(String condition) {
        query = "ORDER BY " + condition;
        return this;
    }

    public QueryBuilder innerJoin(String condition) {
        query = "INNER JOIN " + condition;
        return this;
    }

    public String getQuery() {
        return query;
    }
}
