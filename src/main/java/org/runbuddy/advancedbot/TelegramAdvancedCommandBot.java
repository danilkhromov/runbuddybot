package org.runbuddy.advancedbot;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.Comparator;
import java.util.List;

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
    }

    @Override
    public final void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand()) {
                commandRegistry.executeCommand(this, message);
            }
        } else if (update.hasCallbackQuery()) {
            callbackRegistry.executeCallback(this,update);
        }
    }

    protected final boolean registerCommand(BotCommand botCommand) {
        return commandRegistry.registerCommand(botCommand);
    }

    protected final void registerCallback(BotCallback botCallback) {
        callbackRegistry.registerCallback(botCallback);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
