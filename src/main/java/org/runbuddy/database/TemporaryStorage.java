package org.runbuddy.database;

import org.apache.commons.collections4.map.PassiveExpiringMap;

import java.util.Map;

/**
 * Created by Danil Khromov.
 */
public final class TemporaryStorage {
    private static final Map<String, StringBuilder> quizData = new PassiveExpiringMap<>(1800000);

    public static void addEntry(String userId) {
        StringBuilder answers = new StringBuilder();
        quizData.put(userId, answers);
    }

    public void addAnswer(String userId, String answer) {
        StringBuilder answers = quizData.get(userId)
                .append(answer).append(" ");
    }

    public String getAnswers(String userId) {
        String answers = quizData.get(userId).toString();
        quizData.remove(userId);
        return answers;
    }
}
