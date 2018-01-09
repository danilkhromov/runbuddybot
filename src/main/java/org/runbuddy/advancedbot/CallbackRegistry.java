package org.runbuddy.advancedbot;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class registers and executes provided bot callbacks
 */
final class CallbackRegistry {

    private final Map<String, BotCallback> callbackRegistryMap = new HashMap<>();

    /**
     * Adds provided callback to the Map
     *
     * @param botCallback callback to add
     */
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

    /**
     * Executes callback if said callback exists in Map
     *
     * @param absSender object which sends messages to user
     * @param update    update received by the bot
     * @return          returns true if callback is successfully executed, returns false otherwise
     */
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
