package org.runbuddy.advancedbot;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Danil Khromov.
 */
final class CallbackRegistry {
    private final Map<String, BotCallback> callbackRegistryMap = new HashMap<>();

    final void registerCallback(BotCallback botCallback) {
        List<String> names = botCallback.getCallbackNames();
        for (String name:names) {
            if (callbackRegistryMap.containsKey(name)) {
                // <name> already exists in <callback1>, while registering <callback2>
                throw new IllegalArgumentException("Callback query with this name already exists");
            } else {
                callbackRegistryMap.put(name,botCallback);
            }
        }
    }

    final boolean executeCallback(AbsSender absSender, Update update) {
        String callbackQuery = update.getCallbackQuery().getData();
        if (callbackRegistryMap.containsKey(callbackQuery)) {
            callbackRegistryMap.get(callbackQuery).execute(absSender,
                    update.getCallbackQuery().getFrom(),
                    update.getCallbackQuery());
            return true;
        }
        return false;
    }
}
