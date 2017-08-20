package org.runbuddy.advancedbot;

import org.runbuddy.advancedbot.BotCallback;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danil Khromov.
 */
final class CallbackRegistry {
    private final Map<String, BotCallback> callbackRegistryMap = new HashMap<>();

    final boolean registerCallback(BotCallback botCallback) {
        for (String name:botCallback.getCallbackNames()) {
            if (callbackRegistryMap.containsKey(name)) {
                return false;
            } else {
                callbackRegistryMap.put(name,botCallback);
                return true;
            }
        }
        return true;
    }

    final boolean executeCallback(AbsSender absSender, Update update) {
        if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();
            if (callbackRegistryMap.containsKey(callbackQuery)) {
                callbackRegistryMap.get(callbackQuery).execute(absSender, update.getCallbackQuery().getFrom(), update.getCallbackQuery());
                return true;
            }
        }
        return false;
    }
}
