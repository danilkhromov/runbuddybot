package org.runbuddy;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * Created by Daniil Khromov.
 */
class MessageCreator {
    SendMessage answer = new SendMessage();

    MessageCreator(String messageText, String chatId) {
        answer.setText(messageText)
                .setChatId(chatId);
    }

    SendPhoto sendPhoto() {
        return null;
    }

    DeleteMessage deleteMessage(String chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId)
                .setMessageId(messageId);
        return deleteMessage;
    }

    SendMessage getAnswer() {
        return answer;
    }
}
