package org.runbuddy.database;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniil Khromov.
 */
public class InsertBuilderTest {
    @Test
    public void getInsert() {
        String expected = "INSERT INTO users (chat_id, user_id) VALUES (123, 456)";
        InsertBuilder insertBuilder = new InsertBuilder("users", "chat_id", "user_id")
                .values("123", "456");
        assertEquals(expected, insertBuilder.getInsert());
    }
}