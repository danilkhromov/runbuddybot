package org.runbuddy.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.runbuddy.database.Manager;
import org.runbuddy.matchers.SendMessageMatcher;
import org.runbuddy.messaging.MessageBuilder;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StartCommandTest {

    @Mock private AbsSender absSender;
    @Mock private User user;
    @Mock private Chat chat;
    @Mock private Manager dbManager;

    private StartCommand startCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        startCommand = new StartCommand(dbManager);
    }

    @Test
    public void getStartCommand() throws TelegramApiException {
        SendMessage expectedMessage = new MessageBuilder("1")
                .getMessage("Привет! " +
                        "Я тебе помогу подобрать подходящие кроссовки для твоих тренировок, " +
                        "если ты ответишь на несколько вопросов.");

        when(chat.getId()).thenReturn((long) 1);
        when(user.getId()).thenReturn(1);

        startCommand.execute(absSender, user, chat);

        verify(chat).getId();
        verify(user).getId();
        verify(dbManager).addUser(user.getId().toString());
        verify(absSender).execute(argThat(new SendMessageMatcher(expectedMessage)));
    }
}