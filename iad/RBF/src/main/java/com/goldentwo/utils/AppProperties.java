package com.goldentwo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private static Properties properties = new Properties();

    static {
        InputStream input = null;

        try {
            input = new FileInputStream("build/resources/main/application.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}