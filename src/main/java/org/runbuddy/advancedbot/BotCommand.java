package org.runbuddy.advancedbot;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by Danil Khromov.
 */
public abstract class BotCommand {

    final static String COMMAND_INIT_CHAR = "/";

    private final String commandName;
    private final String commandDescription;

    public BotCommand(String commandName, String commandDescription) {

        if (commandName == null || commandName.isEmpty()) {
            throw new IllegalArgumentException("commandName cannot be null or empty");
        }

        this.commandName = commandName.toLowerCase();
        this.commandDescription = commandDescription;
    }

    final String getCommandName() {
        return commandName;
    }

    public final String getCommandDescription() {
        return commandDescription;
    }

    public abstract void execute(AbsSender absSender, User user, Chat chat);
}
