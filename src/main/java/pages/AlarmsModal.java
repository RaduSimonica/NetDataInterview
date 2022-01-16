package pages;

import alarms.Alarm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.DriverUtils;

import java.util.List;

public class AlarmsModal {

    private DriverUtils driverUtils;

    @FindBy(id = "alarmsModalLabel")
    private WebElement alarmsModalLabel;

    @FindBy(id = "alarms_active")
    private WebElement alarmsActive;

    public AlarmsModal(WebDriver driver) {
        this.driverUtils = new DriverUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public Boolean isAlarmsModalLabelDisplayed() {
        return this.alarmsModalLabel.isDisplayed();
    }

    public void waitForAlarmsModalLabel() {
        this.driverUtils.waitForElementToBeDisplayed(this.alarmsModalLabel);
    }

    public Boolean isAlarmActive(Alarm alarm) {
        List<WebElement> activeAlarms = this.alarmsActive.findElements(By.xpath("//table/tbody/tr/td[1]/span"));
        for (WebElement activeAlarm : activeAlarms) {
            if (activeAlarm.getText().equals(alarm.getInfo())) {
                return true;
            }
        }
        return false;
    }
}
