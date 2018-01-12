package org.runbuddy.callbacks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.runbuddy.database.TemporaryStorage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArchCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;
    @Mock private Message message;

    private ArchCallback archCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        archCallback = new ArchCallback();
    }

    @Test
    public void getArchCallback() throws TelegramApiException {
        TemporaryStorage.getTemporaryStorage().addEntry("1");

        when(user.getId()).thenReturn(1);
        when(callbackQuery.getMessage()).thenReturn(message);

        archCallback.execute(absSender, user, callbackQuery);

        verify(user, atLeastOnce()).getId();
        verify(absSender).sendPhoto(any(SendPhoto.class));
    }

    @Test
    public void getQueryTimeOut() throws TelegramApiException {
        when(user.getId()).thenReturn(2);
        when(callbackQuery.getMessage()).thenReturn(message);

        archCallback.execute(absSender, user, callbackQuery);

        verify(user, times(2)).getId();
        verify(absSender).execute(any(SendMessage.class));
        verify(absSender).execute(any(DeleteMessage.class));
    }

}