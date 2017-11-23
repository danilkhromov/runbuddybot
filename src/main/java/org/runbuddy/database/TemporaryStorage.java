package org.runbuddy.database;

import org.apache.commons.collections4.map.PassiveExpiringMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Danil Khromov.
 */
public final class TemporaryStorage {
    private static final Map<String, StringBuilder> quizData = new PassiveExpiringMap<>(TimeUnit.MINUTES.toMillis(30));

    public static void addEntry(String userId) {
        StringBuilder answers = new StringBuilder();
        quizData.put(userId, answers);
    }

    public static void addAnswer(String userId, String answer) {
        quizData.get(userId)
                .append(answer).append(",");
    }

    public static String getAnswers(String userId) {
        return quizData.get(userId).toString();
    }
}
