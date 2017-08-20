package org.runbuddy.advancedbot;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

/**
 * Created by Danil Khromov.
 */
public abstract class TelegramAdvancedCommandBot extends TelegramLongPollingBot {
    private final CommandRegistry commandRegistry;
    private final CallbackRegistry callbackRegistry;
    private String botUsername;

    public TelegramAdvancedCommandBot(String botUsername) {
        this.botUsername = botUsername;
        this.commandRegistry = new CommandRegistry();
        this.callbackRegistry = new CallbackRegistry();
        //this(ApiContext.getInstance(DefaultBotOptions.class), botUsername);
    }

    @Override
    public final void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand()) {
                if (commandRegistry.executeCommand(this, message)) {
                    return;
                }
            }
        } else if (update.hasCallbackQuery()) {
            if (callbackRegistry.executeCallback(this,update)) {
                return;
            }
        }
    }

    protected final boolean registerCommand(BotCommand botCommand) {
        return commandRegistry.registerCommand(botCommand);
    }

    protected final boolean registerCallback(BotCallback botCallback) {
        return callbackRegistry.registerCallback(botCallback);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
