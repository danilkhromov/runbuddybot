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
        for (String value:values) {
            query.append(value);
            if (!value.equals(values[values.length-1])) {
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
