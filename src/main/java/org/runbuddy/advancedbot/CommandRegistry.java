package org.runbuddy.advancedbot;

import org.runbuddy.advancedbot.BotCommand;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.AbsSender;

import java.util.HashMap;
import java.util.Map;

/**
 * This class registers and executes provided bot commands
 */
final class CommandRegistry {

    private final Map<String, BotCommand> commandRegistryMap = new HashMap<>();

    /**
     * Adds provided command to the Map
     *
     * @param botCommand command to add
     * @return           returns true if command if successfully added, returns false otherwise
     */
    final boolean registerCommand(BotCommand botCommand) {
        if (commandRegistryMap.containsKey(botCommand.getCommandName())) {
            return false;
        }
        commandRegistryMap.put(botCommand.getCommandName(), botCommand);
        return true;
    }

    /**
     * Executes command if said command exists in Map
     *
     * @param absSender object which sends messages to user
     * @param message   message received by the bot
     * @return          returns true if command successfully executed, returns false otherwise
     */
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
