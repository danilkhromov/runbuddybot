package org.runbuddy.database;

/**
 * Created by Danil Khromov.
 */
class SelectBuilder {
    private StringBuilder query;

    SelectBuilder(String condition) {
        query = new StringBuilder()
                .append("SELECT ")
                .append(condition);
    }

    SelectBuilder from(String condition) {
        query.append(" FROM ").append(condition);
        return this;
    }

    SelectBuilder where(String condition) {
        query.append(" WHERE ").append(condition);
        return this;
    }

    SelectBuilder orderBy(String condition) {
        query.append(" ORDER BY ").append(condition);
        return this;
    }

    SelectBuilder innerJoin(String condition) {
        query.append(" INNER JOIN ").append(condition);
        return this;
    }

    SelectBuilder on(String condition) {
        query.append(" ON ").append(condition);
        return this;
    }

    String getQuery() {
        return query.toString();
    }
}
