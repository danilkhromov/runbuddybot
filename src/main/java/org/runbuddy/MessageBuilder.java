package org.runbuddy;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniil Khromov.
 */
class MessageBuilder extends RunBuddyBot{
    private SendMessage message;
    private SendPhoto photo;
    private InlineKeyboardMarkup keyboardMarkup;

    private int buttonsInRow = 1;
    private String chatId;
    private final List<InlineKeyboardButton> buttonList = new ArrayList<>();

    MessageBuilder(String chatId) {
        this.chatId = chatId;
    }

    public MessageBuilder buttonsInRow(int buttonsInRow) {
        this.buttonsInRow = buttonsInRow;
        return this;
    }

    public MessageBuilder setText(String text) {
        message = new SendMessage(chatId, text)
                .setReplyMarkup(keyboardMarkup);
        return this;
    }

    public MessageBuilder setPhoto(String url) {
        photo = new SendPhoto().setPhoto(url).setChatId(chatId)
                .setReplyMarkup(keyboardMarkup);
        return this;
    }

    public MessageBuilder addButton(String label, String callbackData) {
        buttonList.add(new InlineKeyboardButton().setText(label).setCallbackData(callbackData));
        return this;
    }

    public MessageBuilder createKeyboard() {
        keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        for (int i = 0; i < buttonList.size(); i += buttonsInRow) {
            buttonRows.add(buttonList.subList(i, Math.min(buttonList.size(), i + buttonsInRow)));
        }

        /*for (InlineKeyboardButton button: buttonList) {
            if (buttonsInRow > buttonRow.size()) {
                buttonRow.add(button);
            } else {
                buttonRows.add(buttonRow);
                buttonRow = new ArrayList<>();
                buttonRow.add(button);
            }
        }*/

        keyboardMarkup.setKeyboard(buttonRows);
        return this;
    }

    /*public void deletePreviousMessage (int messageId) throws TelegramApiException{
        DeleteMessage delete = new DeleteMessage()
                .setMessageId(messageId)
                .setChatId(chatId);
        deleteMessage(delete);
    }*/

    public MessageBuilder sendMessage() {
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setPhoto() throws TelegramApiException {
        sendPhoto(photo);
    }
}
