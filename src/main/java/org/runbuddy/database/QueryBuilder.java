package org.runbuddy.database;

/**
 * Created by Daniil Khromov.
 */
class QueryBuilder {
    private StringBuilder query;

    QueryBuilder insertInto(String tableName, String... columns) {
        query = new StringBuilder()
                .append("INSERT INTO ")
                .append(tableName)
                .append(" (");

        for (String column:columns) {
            query.append(column);
            if (!column.equals(columns[columns.length -1])) {
                query.append(", ");
            } else {
                query.append(") ");
            }
        }
        return this;
    }

    QueryBuilder values(String... values) {
        query.append("VALUES (");
        for (int i = 0; i < values.length; i++) {
            query.append(values[i]);
            if (i != values.length - 1) {
                query.append(", ");
            } else {
                query.append(")");
            }
        }
        return this;
    }

    QueryBuilder deleteFrom(String table) {
        query = new StringBuilder()
                .append("DELETE FROM ")
                .append(table);
        return this;
    }

    QueryBuilder select(String... columns) {
        query = new StringBuilder()
                .append("SELECT ");
        for (String column:columns) {
            query.append(column);
            if (!column.equals(columns[columns.length -1])) {
                query.append(", ");
            }
        }
        return this;
    }

    QueryBuilder from(String table) {
        query.append(" FROM ").append(table);
        return this;
    }

    QueryBuilder where(String column) {
        query.append(" WHERE ").append(column);
        return this;
    }

    QueryBuilder eq(String value) {
        query.append(" = ").append(value);
        return this;
    }

    QueryBuilder and(String condition) {
        query.append(" AND ").append(condition);
        return this;
    }

    QueryBuilder orderBy(String condition) {
        query.append(" ORDER BY ").append(condition);
        return this;
    }
    
    QueryBuilder limit(int number) {
        query.append(" LIMIT ").append(String.valueOf(number));
        return this;
    }

    QueryBuilder innerJoin(String condition) {
        query.append(" INNER JOIN ").append(condition);
        return this;
    }

    QueryBuilder on(String condition) {
        query.append(" ON ").append(condition);
        return this;
    }

    String create() {
        return query.toString();
    }
}
