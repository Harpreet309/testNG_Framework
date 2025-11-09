package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    static {
        prop = new Properties();
        try {
            InputStream input = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("Config.properties");

            if(input == null){
                throw new RuntimeException("Config.properties file not found in classpath!");
            }

            prop.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file.");
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }

    public static int getInt(String key, int defaultVal) {
        String v = prop.getProperty(key);
        if (v == null) return defaultVal;
        return Integer.parseInt(v);
    }
}