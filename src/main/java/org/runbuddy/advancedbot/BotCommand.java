package org.runbuddy.advancedbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Created by Danil Khromov.
 */
public abstract class BotCommand {

    private static final Logger logger = LogManager.getLogger();

    final static String COMMAND_INIT_CHAR = "/";

    private final String commandName;
    private final String commandDescription;

    /**
     * Creates a bot command
     *
     * @throws IllegalArgumentException if no name have been provided
     * @param commandName name of the command
     * @param commandDescription description of the command
     */
    public BotCommand(String commandName, String commandDescription) {

        if (commandName == null || commandName.isEmpty()) {
            logger.warn("Failed to register command. Command name should not be empty");
            throw new IllegalArgumentException("commandName cannot be null or empty");
        }

        this.commandName = commandName.toLowerCase();
        this.commandDescription = commandDescription;
    }

    /**
     *  Returns name of the command
     */
    final String getCommandName() {
        return commandName;
    }

    public final String getCommandDescription() {
        return commandDescription;
    }

    public abstract void execute(AbsSender absSender, User user, Chat chat);
}
