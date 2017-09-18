package org.runbuddy;

import org.runbuddy.advancedbot.TelegramAdvancedCommandBot;
import org.runbuddy.callbacks.*;
import org.runbuddy.commands.MenuCommand;
import org.runbuddy.commands.StartCommand;

/**
 * Created by Daniil Khromov.
 */
public class RunBuddyBot extends TelegramAdvancedCommandBot {

    RunBuddyBot() {
        super("runbuddybot");

        registerCommand(new StartCommand());
        registerCommand(new MenuCommand());

        registerCallback(new GenderCallback());
        registerCallback(new WeightCallback());
        registerCallback(new ArchCallback());
        registerCallback(new TypeCallback());
        registerCallback(new RoadCallback());
        registerCallback(new ResultCallback());
        registerCallback(new AnotherShoeCallback());
        registerCallback(new MenuCallback());
        registerCallback(new GaitCallback());
    }

    @Override
    public String getBotToken() {
        return "443181452:AAGdpxqqJfejzLkuL3SQL8VSPZ_9rug91TM";
    }
}
