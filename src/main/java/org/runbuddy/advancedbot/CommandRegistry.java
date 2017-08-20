package org.runbuddy.advancedbot;

import org.runbuddy.advancedbot.BotCommand;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danil Khromov.
 */
final class CommandRegistry {
    private final Map<String, BotCommand> commandRegistryMap = new HashMap<>();

    final boolean registerCommand(BotCommand botCommand) {
        if (commandRegistryMap.containsKey(botCommand.getCommandName())) {
            return false;
        }

        commandRegistryMap.put(botCommand.getCommandName(), botCommand);
        return true;
    }

    final boolean executeCommand(AbsSender absSender, Message message) {
        if (message.hasText()) {
            String text = message.getText();
            if (text.startsWith(BotCommand.COMMAND_INIT_CHAR)) {
                String commandName = text.substring(1);
                if (commandRegistryMap.containsKey(commandName)) {
                    commandRegistryMap.get(commandName).execute(absSender, message.getFrom(), message.getChat());
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
