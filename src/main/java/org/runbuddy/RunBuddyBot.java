package org.runbuddy;

import org.runbuddy.callbacks.*;
import org.runbuddy.commands.StartCommand;
import org.runbuddy.advancedbot.TelegramAdvancedCommandBot;

/**
 * Created by Daniil Khromov.
 */
public class RunBuddyBot extends TelegramAdvancedCommandBot {

    RunBuddyBot() {
        super("runbuddybot");

        registerCommand(new StartCommand());
        registerCallback(new GenderCallback());
        registerCallback(new WeightCallback());
        registerCallback(new ArchCallback());
        registerCallback(new TypeCallback());
        registerCallback(new RoadCallback());
        registerCallback(new ResultCallback());
        registerCallback(new AnotherShoeCallback());
    }

    @Override
    public String getBotToken() {
        return "443181452:AAGdpxqqJfejzLkuL3SQL8VSPZ_9rug91TM";
    }
}
