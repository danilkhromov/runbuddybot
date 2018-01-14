package org.runbuddy.callbacks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.runbuddy.database.TemporaryStorage;
import org.runbuddy.matchers.SendMessageMatcher;
import org.runbuddy.matchers.SendPhotoMatcher;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TypeCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;
    @Mock private Message message;

    private TypeCallback typeCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        typeCallback = new TypeCallback();
    }

    @Test
    public void getTypeCallback() throws TelegramApiException {
        SendPhoto expectedPhoto = new MessageBuilder("1")
                .getPhoto("AgADAgADJ6kxG44WwEliaUK8X85dbBEIMw4ABOtQwdQENceQvsYBAAEC",
                        "Расстояние или скорость?");

        TemporaryStorage.getTemporaryStorage().addEntry("1");

        when(user.getId()).thenReturn(1);
        when(callbackQuery.getMessage()).thenReturn(message);

        typeCallback.execute(absSender, user, callbackQuery);

        verify(user, times(3)).getId();
        verify(absSender).sendPhoto(argThat(new SendPhotoMatcher(expectedPhoto)));
        verify(absSender).execute(any(DeleteMessage.class));
    }

    @Test
    public void getQueryTimeOut() throws TelegramApiException {
        SendMessage expectedMessage = new MessageBuilder("2")
                .getMessage("Похоже результаты запроса устарели. " +
                        "Пройти тест заново?");

        when(user.getId()).thenReturn(2);
        when(callbackQuery.getMessage()).thenReturn(message);

        typeCallback.execute(absSender, user, callbackQuery);

        verify(user, times(2)).getId();
        verify(absSender).execute(argThat(new SendMessageMatcher(expectedMessage)));
        verify(absSender).execute(any(SendMessage.class));
    }
}