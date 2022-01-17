package utilities;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.config.ConfigData;

public class DriverUtils {

    private WebDriver driver;

    public DriverUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateHome() {
        this.driver.get(ConfigData.getAppHostname());
    }

    public void refresh() {
        this.driver.navigate().refresh();
    }

    public void waitForSeconds(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {
        }
    }

    public void waitForElementToBeDisplayed(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(this.driver, ConfigData.getDriverImplicitWait());
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException ignored) {
            waitForSeconds(2);
        }
    }
}
