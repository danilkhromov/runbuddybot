package org.runbuddy.messaging;


import org.runbuddy.RunBuddyBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by Daniil Khromov.
 */
public class MessageHandler extends RunBuddyBot {
    private SendType type;

    public MessageHandler(MessageBuilder answer) {
        type = answer.getType();
    }

    public void sendAnswer (MessageBuilder answer) throws TelegramApiException {
        switch (type) {
            case TEXT:
                execute(answer.getMessage());
                break;
            case PHOTO:
                sendPhoto(answer.getPhoto());
                break;
        }

        if (answer.getDeletePrevious()) {
            execute(answer.getDelete());
        }
    }
}
