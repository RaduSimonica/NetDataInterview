package pages;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.DriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.config.ConfigData;

public class HomePage {

    private static final Logger LOGGER = LogManager.getLogger();

    private DriverUtils driverUtils;

    @FindBy(id = "root")
    private WebElement pageRoot;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/header/div[2]/div[4]/div[1]")
    private WebElement alarmsModal;

    @FindBy(id = "alarmsModalLabel")
    private WebElement alarmsModalLabel;

    public HomePage(WebDriver driver) {
        this.driverUtils = new DriverUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public Boolean isPageRootDisplayed() {
        try {
            return this.pageRoot.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitForPageRootToBeDisplayed() {
        LOGGER.log(Level.INFO, "Waiting for pageRoot element to be displayed.");
        for (int i = 0; i < ConfigData.getDriverAppRestartWait(); i++) {
            if (isPageRootDisplayed()) {
                return;
            }
            this.driverUtils.refresh();
            this.driverUtils.waitForSeconds(1);
        }
    }

    public void clickAlarmsModal() {
        this.alarmsModal.click();
    }
}
