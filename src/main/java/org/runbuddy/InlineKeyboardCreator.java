package org.runbuddy;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniil Khromov.
 */
class InlineKeyboardCreator extends MessageCreator{
    private InlineKeyboardMarkup keyboardMarkup;
    private List<List<InlineKeyboardButton>> rowsInLine;
    private List<InlineKeyboardButton> rowInLine;

    private int numberOfButtons;

    InlineKeyboardCreator(String message, String chatId) {
        super(message, chatId);
    }

    InlineKeyboardCreator(String message, String chatId, int numberOfButtons) {
        super(message, chatId);

        keyboardMarkup = new InlineKeyboardMarkup();
        rowsInLine = new ArrayList<>();
        rowInLine = new ArrayList<>();

        this.numberOfButtons = numberOfButtons;
    }

    void addButton(String name, String callbackData) {
        if (rowInLine.size() != numberOfButtons - 1) {
            //rowsInLine.add(rowInLine);
            //rowInLine = new ArrayList<>();
            rowInLine.add(new InlineKeyboardButton().setText(name).setCallbackData(callbackData));
        } else {
            rowsInLine.add(rowInLine);
            rowInLine.add(new InlineKeyboardButton().setText(name).setCallbackData(callbackData));
        }
    }

    void createKeyboard() {
        keyboardMarkup.setKeyboard(rowsInLine);
        answer.setReplyMarkup(keyboardMarkup);
    }

    SendMessage getKeyboard() {
        return answer;
    }
}
