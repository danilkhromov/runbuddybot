package org.runbuddy;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniil Khromov.
 */
class InlineKeyboardCreator {
    InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
    List<InlineKeyboardButton> rowInLine = new ArrayList<>();

    int numberOfButtons;
    int numberOfRows;

    InlineKeyboardCreator(int numberOfButtons, int numberOfRows) {
        this.numberOfButtons = numberOfButtons;
        this.numberOfRows = numberOfRows;
    }

    void addButton(String name, String callbackData) {
        if (rowInLine.size() != numberOfButtons - 1) {
            rowInLine.add(new InlineKeyboardButton().setText(name).setCallbackData(callbackData));
        } else {
            rowInLine.add(new InlineKeyboardButton().setText(name).setCallbackData(callbackData));
            rowsInLine.add(rowInLine);
        }
    }

    InlineKeyboardMarkup createKeyboard() {
        return keyboardMarkup.setKeyboard(rowsInLine);
    }
}
