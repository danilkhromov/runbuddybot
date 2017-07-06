package org.runbuddy.database;

/**
 * Created by Danil Khromov.
 */
class InsertBuilder {
    private StringBuilder query;

    InsertBuilder(String tableName, String... columns) {
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
    }

    InsertBuilder values(String... values) {
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

    String getInsert() {
        return query.toString();
    }
}
