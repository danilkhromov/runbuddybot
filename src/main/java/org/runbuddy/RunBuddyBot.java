package org.runbuddy;


import org.runbuddy.commands.StartCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;

/**
 * Created by Daniil Khromov.
 */
class RunBuddyBot extends TelegramLongPollingCommandBot {

    RunBuddyBot(){
        register(new StartCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        //do nothing
    }

    @Override
    public String getBotUsername() {
        return "RunBuddy";
    }

    @Override
    public String getBotToken() {
        return "443181452:AAFugk_yEnUJRC6Ovg72ctmu3j9T52WwGe4";
    }
}
