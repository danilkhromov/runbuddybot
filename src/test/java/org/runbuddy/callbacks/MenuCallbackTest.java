package org.runbuddy.callbacks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MenuCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;
    @Mock private Message message;

    private MenuCallback menuCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        menuCallback = new MenuCallback();
    }

    @Test
    public void getMenuCallback() throws TelegramApiException {
        when(user.getId()).thenReturn(1);
        when(callbackQuery.getMessage()).thenReturn(message);

        menuCallback.execute(absSender, user, callbackQuery);

        verify(user).getId();
        verify(absSender).execute(any(SendMessage.class));
        verify(absSender).execute(any(DeleteMessage.class));
    }
}