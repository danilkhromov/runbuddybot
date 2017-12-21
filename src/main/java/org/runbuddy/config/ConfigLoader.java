package org.runbuddy.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Daniil Khromov.
 */
public class ConfigLoader {

    private static ConfigLoader configLoader;
    private Properties botConfig;

    private ConfigLoader(){
        try (InputStream input = new FileInputStream("botconfig.properties")) {
            botConfig = new Properties();
            botConfig.load(input);
        } catch (FileNotFoundException e) {
            System.out.println("botconfig.properties not found");
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigLoader getInstace() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
            return configLoader;
        } else {
            return configLoader;
        }
    }

    public String getProperty(String propertyName) {
        return botConfig.getProperty(propertyName);
    }
}
