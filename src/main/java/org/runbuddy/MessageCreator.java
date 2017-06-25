package org.runbuddy;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * Created by Daniil Khromov.
 */
public class MessageCreator {
    SendMessage answer = new SendMessage();

    MessageCreator(String messageText, long chatId) {
        answer.setText(messageText)
                .setChatId(chatId);
    }

    void addInlineKeyboard(InlineKeyboardMarkup keyboardMarkup) {
        answer.setReplyMarkup(keyboardMarkup);
    }

    SendMessage getAnswer() {
        return answer;
    }
}
