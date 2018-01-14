package org.runbuddy.callbacks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.runbuddy.database.Manager;
import org.runbuddy.database.TemporaryStorage;
import org.runbuddy.matchers.SendMessageMatcher;
import org.runbuddy.matchers.SendPhotoMatcher;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.mockito.Mockito.*;

/**
 * Created by Daniil Khromov.
 */
public class AnotherShoeCallbackTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private CallbackQuery callbackQuery;
    @Mock private Manager dbManager;

    private AnotherShoeCallback anotherShoeCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        anotherShoeCallback = new AnotherShoeCallback(dbManager);

        TemporaryStorage.getTemporaryStorage().addEntry("1");
    }

    @Test
    public void getAnotherShoe() throws TelegramApiException {
        String[] shoe = new String[3];
        shoe[0] = "name";
        shoe[1] = "image";
        shoe[2] = "url";

        SendPhoto expectedPhoto = new MessageBuilder("1")
                .getPhoto(shoe[1], shoe[0]);

        when(user.getId()).thenReturn(1);
        when(dbManager.getShoe("1","")).thenReturn(shoe);

        anotherShoeCallback.execute(absSender, user, callbackQuery);

        verify(user, times(2)).getId();
        verify(absSender).sendPhoto(argThat(new SendPhotoMatcher(expectedPhoto)));
    }

    @Test
    public void getNoMoreShoes() throws TelegramApiException {
        SendMessage expectedMessage = new MessageBuilder("1")
                .getMessage("Похоже у нас закончились подходящие модели. " +
                        "Пройти тест заново?");

        when(user.getId()).thenReturn(1);
        when(dbManager.getShoe("1","")).thenReturn(new String[3]);

        anotherShoeCallback.execute(absSender, user, callbackQuery);

        verify(user, times(2)).getId();
        verify(absSender).execute(argThat(new SendMessageMatcher(expectedMessage)));
    }

    @Test
    public void getQueryTimeOut() throws TelegramApiException {
        SendMessage expectedMessage = new MessageBuilder("2")
                .getMessage("Похоже результаты запроса устарели. " +
                        "Пройти тест заново?");

        when(user.getId()).thenReturn(2);

        anotherShoeCallback.execute(absSender, user, callbackQuery);

        verify(user).getId();
        verify(absSender).execute(argThat(new SendMessageMatcher(expectedMessage)));
        verify(absSender, never()).sendPhoto(any(SendPhoto.class));
    }
}