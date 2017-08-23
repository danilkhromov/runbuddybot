package org.runbuddy.advancedbot;

import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Danil Khromov.
 */
public abstract class BotCallback {
    // Set -> HashSet/TreeSet
    private final List<String> callbackNames;

    /**
     * Creates a bot callback.
     *
     * @throws IllegalArgumentException if no names ahve bneen provided
     * @param names list of names to register for this bot callback.
     */
    public BotCallback(String... names) {
        if (names.length == 0) {
            throw new IllegalArgumentException("Callback must have at least one name");
        }

        callbackNames = new ArrayList<>();
        callbackNames.addAll(Arrays.asList(names));
    }

    final List<String> getCallbackNames() {
        return callbackNames;
    }

    public abstract void execute(AbsSender absSender, User user, CallbackQuery callbackQuery);
}
