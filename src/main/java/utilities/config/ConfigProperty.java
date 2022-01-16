package utilities.config;

public enum ConfigProperty {

    DRIVER_CHROME_PATH_LINUX("driver.chrome.path.linux"),
    DRIVER_CHROME_ENV_VARIABLE("driver.chrome.env_variable"),
    DRIVER_WAIT_IMPLICIT("driver.wait.implicit"),
    DRIVER_WAIT_PAGE_LOAD("driver.wait.page_load"),
    DRIVER_WAIT_APP_RESTART("driver.wait.app_restart"),
    GRID_HOSTNAME("grid.hostname"),
    APP_HOSTNAME("app.hostname"),
    ALARM_TMP_DIR("alarm.tmp.dir");

    private final String value;

    ConfigProperty(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
