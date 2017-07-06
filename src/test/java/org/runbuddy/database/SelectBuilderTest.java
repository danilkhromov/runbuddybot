package org.runbuddy.database;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniil Khromov.
 */
public class SelectBuilderTest {
    @Test
    public void testBuilder() {
        String conditions = "condition1, condition2, condition3, condition4, condition5";
        String expected = "SELECT condition1, condition2, condition3, condition4, condition5 " +
                "FROM table1 " +
                "WHERE this = that " +
                "ORDER BY RANDOM() " +
                "INNER JOIN table2 ON table2.column = table1.column " +
                "INNER JOIN table3 ON table3.column = table1.column " +
                "INNER JOIN table4 ON table4.column = table1.column";
        SelectBuilder selectBuilder = new SelectBuilder(conditions)
                .from("table1")
                .where("this = that")
                .orderBy("RANDOM()")
                .innerJoin("table2").on("table2.column = table1.column")
                .innerJoin("table3").on("table3.column = table1.column")
                .innerJoin("table4").on("table4.column = table1.column");
        //selectBuilder.getQuery();
        assertEquals("this shit must be the same", expected, selectBuilder.getQuery());
    }
}