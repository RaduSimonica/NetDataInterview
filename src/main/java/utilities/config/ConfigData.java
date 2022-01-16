package utilities.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ConfigData {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONFIG = "config.properties";

    public static String getChromeDriverEnvVariable() {
        return getGenericProperty(ConfigProperty.DRIVER_CHROME_ENV_VARIABLE);
    }

    public static String getChromeDriverPath() {
        // TODO: Make this smarter and parametrize the driver version in the config file.
        // TODO: Add proper support for all major OSs
        return getGenericProperty(ConfigProperty.DRIVER_CHROME_PATH_LINUX);
    }

    public static Integer getDriverImplicitWait() {
        ConfigProperty property = ConfigProperty.DRIVER_WAIT_IMPLICIT;
        try {
            return Integer.parseInt(Objects.requireNonNull(getGenericProperty(property)));
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.log(Level.ERROR, String.format("Could not parse {%s} property as Integer.", property.get()));
            return 0;
        }
    }

    public static Integer getDriverPageLoadWait() {
        ConfigProperty property = ConfigProperty.DRIVER_WAIT_PAGE_LOAD;
        try {
            return Integer.parseInt(Objects.requireNonNull(getGenericProperty(property)));
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.log(Level.ERROR, String.format("Could not parse {%s} property as Integer.", property.get()));
            return 0;
        }
    }

    public static Integer getDriverAppRestartWait() {
        ConfigProperty property = ConfigProperty.DRIVER_WAIT_APP_RESTART;
        try {
            return Integer.parseInt(Objects.requireNonNull(getGenericProperty(property)));
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.log(Level.ERROR, String.format("Could not parse {%s} property as Integer.", property.get()));
            return 0;
        }
    }

    public static String getGridHostname() {
        return getGenericProperty(ConfigProperty.GRID_HOSTNAME);
    }

    public static String getAppHostname() {
        return getGenericProperty(ConfigProperty.APP_HOSTNAME);
    }

    public static String getAlarmTmpDir() {
        return getGenericProperty(ConfigProperty.ALARM_TMP_DIR);
    }

    private static String getGenericProperty(ConfigProperty property) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG)) {
            Properties properties = new Properties();

            if (inputStream != null) {
                properties.load(inputStream);
                return properties.getProperty(property.get());
            }
        } catch (Exception e) {
            LOGGER.log(
                    Level.ERROR,
                    String.format("Could not retrieve %s property from %s file", property, CONFIG)
            );
        }

        return null;
    }
}
