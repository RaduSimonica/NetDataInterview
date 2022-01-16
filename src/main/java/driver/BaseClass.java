package driver;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import utilities.DriverUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.config.ConfigData;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    private static final Logger LOGGER = LogManager.getLogger();

    protected WebDriver driver;
    protected DriverUtils driverUtils;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        setupChromeDriver();
        this.driverUtils = new DriverUtils(this.driver);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        this.driver.quit();
    }

    private void setupChromeDriver() {
        System.setProperty(ConfigData.getChromeDriverEnvVariable(), ConfigData.getChromeDriverPath());
        String gridHostname = ConfigData.getGridHostname();
        URL hubUrl = null;
        try {
            hubUrl = new URL(gridHostname);
        } catch (MalformedURLException e) {
            LOGGER.log(Level.ERROR, String.format("Could not find a Selenium Grid at %s hostname.", gridHostname));
        }
        try {
            this.driver = new RemoteWebDriver(hubUrl, setOptions());
        } catch (Exception e) {
            LOGGER.log(
                    Level.ERROR,
                    String.format("Could not start a new Webdriver on Selenium grid hub: %s", hubUrl.toString())
            );
            Assert.fail(String.format("Could not start a new Webdriver on Selenium grid hub: %s", hubUrl));
        }
        this.driver.manage().timeouts().implicitlyWait(ConfigData.getDriverImplicitWait(), TimeUnit.SECONDS);
        this.driver.manage().timeouts().pageLoadTimeout(ConfigData.getDriverPageLoadWait(), TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
    }

    private ChromeOptions setOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("enable-automation");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--dns-prefetch-disable");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        return chromeOptions;
    }
}
