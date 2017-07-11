package org.runbuddy.database;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniil Khromov.
 */
public class QueryBuilderTest {
    @Test
    public void insertInto() {
        String expected = "INSERT INTO table (column1, column2, column3) VALUES (value1, value2, value3)";
        String created = new QueryBuilder()
                .insertInto("table", "column1", "column2", "column3")
                .values("value1", "value2", "value3")
                .create();
        assertEquals(expected, created);
    }

    @Test
    public void select() {
        String expected = "SELECT column1, column2, column3 " +
                "FROM table " +
                "WHERE this = that " +
                "AND a = b " +
                "ORDER BY RANDOM() " +
                "LIMIT 10 " +
                "INNER JOIN table2 ON table2.column1 = table1.column1";
        String created = new QueryBuilder()
                .select("column1", "column2", "column3")
                .from("table")
                .where("this").eq("that")
                .and("a").eq("b")
                .orderBy("RANDOM()")
                .limit(10)
                .innerJoin("table2").on("table2.column1").eq("table1.column1")
                .create();
        assertEquals(expected,created);
    }

    @Test
    public void deleteFrom() {
        String expected = "DELETE FROM table WHERE this = that";
        String created = new QueryBuilder()
                .deleteFrom("table")
                .where("this").eq("that")
                .create();
        assertEquals(expected, created);
    }
}