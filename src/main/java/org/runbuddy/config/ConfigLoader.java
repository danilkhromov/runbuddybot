package org.runbuddy.config;

import java.io.*;
import java.util.Properties;

/**
 * Created by Daniil Khromov.
 */
public class ConfigLoader {

    private static ConfigLoader configLoader;
    private Properties botConfig;

    private ConfigLoader() {
        InputStream stream = ConfigLoader.class.getResourceAsStream("/botconfig.properties");
        if (stream != null) {
            try (InputStream tmp = stream) {
                botConfig = new Properties();
                botConfig.load(tmp);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        } else {
            throw new RuntimeException("Config file not found");
        }
    }

    public static ConfigLoader getInstace() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getProperty(String propertyName) {
        return botConfig.getProperty(propertyName);
    }
}
