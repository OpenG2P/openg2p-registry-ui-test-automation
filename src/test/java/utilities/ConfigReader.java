package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("../resources/testconfig.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read test configuration file");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
