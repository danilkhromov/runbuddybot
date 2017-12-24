package org.runbuddy.callbacks;

import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.runbuddy.database.TemporaryStorage;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Daniil Khromov.
 */
public class AnotherShoeCallbackTest {

    @Test
    public void testExpired() throws TelegramApiException {
        String userId = "1";
        int userIdInt = Integer.valueOf(userId);
        MessageBuilder answer = new MessageBuilder("1");

        AnotherShoeCallback command = new AnotherShoeCallback();
        SendMessage expiredMessage = command.getExpiredMessage(answer);

        AbsSender absSender = mock(AbsSender.class);
//        when(absSender.execute(argThat(new SendMessageMatcher(expiredMessage)))).thenReturn(new Message());

        User user = mock(User.class);
        when(user.getId()).thenReturn(userIdInt);

        command.execute(absSender, user, null);


        verify(user, times(1)).getId();
        verify(absSender).execute(argThat(new SendMessageMatcher(expiredMessage)));
        verifyNoMoreInteractions(user, absSender);
    }

    class SendMessageMatcher implements ArgumentMatcher<SendMessage> {

        private final SendMessage expected;

        public SendMessageMatcher(SendMessage expected) {
            this.expected = expected;
        }

        @Override
        public boolean matches(SendMessage actual) {
            return expected.getText().equals(actual.getText());
        }
    }
}