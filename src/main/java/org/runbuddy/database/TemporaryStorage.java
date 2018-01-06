package org.runbuddy.database;

import org.apache.commons.collections4.map.PassiveExpiringMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Danil Khromov.
 * TODO method syncronization
 */
public class TemporaryStorage {

    private static volatile TemporaryStorage temporaryStorage;
    private volatile Map<String, StringBuilder> quizData;

    private TemporaryStorage(){
        quizData = new PassiveExpiringMap<>(TimeUnit.MINUTES.toMillis(30));
    }

    public static TemporaryStorage getTemporaryStorage() {
        if (temporaryStorage == null) {
            synchronized (TemporaryStorage.class) {
                if (temporaryStorage == null) {
                    temporaryStorage = new TemporaryStorage();
                }
             }
        }
        return temporaryStorage;
    }

    public boolean containsEntry(String userId) {
        return quizData.containsKey(userId);
    }

    public void addEntry(String userId) {
        StringBuilder answers = new StringBuilder();
        quizData.put(userId, answers);
    }

    public void addAnswer(String userId, String answer) {
        quizData.get(userId)
                .append(answer).append(",");
    }

    public String getAnswers(String userId) {
        return quizData.get(userId).toString();
    }
}
