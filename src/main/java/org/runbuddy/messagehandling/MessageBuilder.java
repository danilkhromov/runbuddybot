package org.runbuddy.messagehandling;

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
    private SendMessage message;
    private SendPhoto photo;
    private InlineKeyboardMarkup keyboardMarkup;
    private DeleteMessage delete;
    private SendType type;

    private boolean flag;
    private int buttonsInRow = 1;
    private String chatId;
    private final List<InlineKeyboardButton> buttonList = new ArrayList<>();

    public MessageBuilder(String chatId) {
        this.chatId = chatId;
        flag = false;
    }

    public MessageBuilder(String chatId, int messageId) {
        this.chatId = chatId;
        flag = true;
        delete = new DeleteMessage()
                .setChatId(chatId)
                .setMessageId(messageId);
    }

    boolean getFlag() {
        return flag;
    }

    SendType getType() {
        return type;
    }

    SendMessage getMessage() {
        return message;
    }

    SendPhoto getPhoto() {
        return photo;
    }

    DeleteMessage getDelete() {
        return delete;
    }

    public MessageBuilder buttonsInRow(int buttonsInRow) {
        this.buttonsInRow = buttonsInRow;
        return this;
    }

    public MessageBuilder setText(String text) {
        message = new SendMessage(chatId, text)
                .setReplyMarkup(keyboardMarkup);
        type = SendType.TEXT;
        return this;
    }

    public MessageBuilder setPhoto(String url, String caption) {
        photo = new SendPhoto()
                .setPhoto(url)
                .setCaption(caption)
                .setChatId(chatId)
                .setReplyMarkup(keyboardMarkup);
        type = SendType.PHOTO;
        return this;
    }

    public MessageBuilder addButton(String label, String callbackData) {
        buttonList.add(new InlineKeyboardButton().setText(label).setCallbackData(callbackData));
        return this;
    }

    public MessageBuilder createKeyboard() {
        keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();

        for (int i = 0; i < buttonList.size(); i += buttonsInRow) {
            buttonRows.add(buttonList.subList(i, Math.min(buttonList.size(), i + buttonsInRow)));
        }

        keyboardMarkup.setKeyboard(buttonRows);
        return this;
    }
}
