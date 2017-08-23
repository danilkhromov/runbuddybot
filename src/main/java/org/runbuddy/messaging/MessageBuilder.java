package org.runbuddy.messaging;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniil Khromov.
 */
public class MessageBuilder {
    private InlineKeyboardMarkup keyboardMarkup;

    private int buttonsInRow = 1;
    private String chatId;
    private final List<InlineKeyboardButton> buttonList = new ArrayList<>();

    public MessageBuilder(String chatId) {
        this.chatId = chatId;
    }

    public SendMessage getMessage(String text) {
        createKeyboard();
        return new SendMessage(chatId, text)
                .setReplyMarkup(keyboardMarkup);
    }

    public SendPhoto getPhoto(String url, String caption) {
        createKeyboard();
        return new SendPhoto()
                .setPhoto(url)
                .setCaption(caption)
                .setChatId(chatId)
                .setReplyMarkup(keyboardMarkup);
    }

    public DeleteMessage getDelete(int messageId) {
        return new DeleteMessage()
                .setChatId(chatId)
                .setMessageId(messageId);
    }

    public MessageBuilder buttonsInRow(int buttonsInRow) {
        if (buttonsInRow < 1) {
            throw new IllegalArgumentException("Buttons in row should be a positive number");
        }
        this.buttonsInRow = buttonsInRow;
        return this;
    }

    public MessageBuilder addButton(String label, String callbackData) {
        buttonList.add(new InlineKeyboardButton().setText(label).setCallbackData(callbackData));
        return this;
    }

    public MessageBuilder addUrl(String label, String url) {
        buttonList.add(new InlineKeyboardButton().setText(label).setUrl(url));
        return this;
    }

    private void createKeyboard() {
        keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();

        for (int i = 0; i < buttonList.size(); i += buttonsInRow) {
            buttonRows.add(buttonList.subList(i, Math.min(buttonList.size(), i + buttonsInRow)));
        }

        keyboardMarkup.setKeyboard(buttonRows);
    }
}
