package org.runbuddy.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MenuCommandTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private Chat chat;

    private MenuCommand menuCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        menuCommand = new MenuCommand();
    }

    @Test
    public void getMenuCommand() throws TelegramApiException {
        ArgumentCaptor<SendMessage> sendMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        String expectedText = "Меню бота:";

        when(chat.getId()).thenReturn((long) 1);

        menuCommand.execute(absSender, user, chat);

        verify(absSender).execute(sendMessageArgumentCaptor.capture());
        assertEquals(expectedText, sendMessageArgumentCaptor.getValue().getText());
    }
}