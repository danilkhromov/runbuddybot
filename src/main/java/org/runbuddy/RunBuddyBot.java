package org.runbuddy;

import org.runbuddy.advancedbot.TelegramAdvancedCommandBot;
import org.runbuddy.callbacks.*;
import org.runbuddy.commands.MenuCommand;
import org.runbuddy.commands.StartCommand;
import org.runbuddy.config.ConfigLoader;
import org.runbuddy.database.Manager;

/**
 * Created by Daniil Khromov.
 */
public class RunBuddyBot extends TelegramAdvancedCommandBot {

    RunBuddyBot(Manager dbManager) {
        super(ConfigLoader.getInstace().getProperty("username"));

        registerCommand(new StartCommand(dbManager));
        registerCommand(new MenuCommand());

        registerCallback(new GenderCallback(dbManager));
        registerCallback(new WeightCallback());
        registerCallback(new ArchCallback());
        registerCallback(new TypeCallback());
        registerCallback(new RoadCallback());
        registerCallback(new ResultCallback(dbManager));
        registerCallback(new AnotherShoeCallback(dbManager));
        registerCallback(new MenuCallback());
        registerCallback(new GaitCallback());
    }

    @Override
    public String getBotToken() {
        return ConfigLoader.getInstace().getProperty("token");
    }
}
