package Tests;

import alarms.Alarm;
import alarms.Lookup;
import alarms.enums.Chart;
import driver.BaseClass;
import enums.Path;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AlarmsModal;
import pages.HomePage;
import utilities.Runner;
import utilities.Scriptlets;

import java.io.File;


public class CheckAlarm extends BaseClass {

    @Test
    public void testAlarm() {
        // Setup test-data
        Alarm alarm = Alarm.builder()
                .name("ram_usage_30")
                .chart(Chart.SYSTEM_RAM)
                .lookup(
                        new Lookup(
                                "average",
                                "-1m",
                                "percentage",
                                "on used"
                        )
                )
                .units("%")
                .frequency("1m")
                .warning("$this > 10")
                .critical("$this > 30")
                .info("The percentage of RAM used by the system.")
                .build();

        alarm.buildAsFile();

        Runner runner = new Runner();
        runner.deleteFileFromApp(Path.ALERTS.get() + alarm.getAlarmFile().getName());
        runner.copyFileToApp(alarm.getAlarmFile().getPath(), Path.ALERTS.get());
        runner.restartNetdataContainer();

        // Frontend
        this.driverUtils.navigateHome();

        HomePage homePage = new HomePage(this.driver);
        homePage.waitForPageRootToBeDisplayed();
        Assert.assertTrue(homePage.isPageRootDisplayed(), "Application is running.");

        homePage.clickAlarmsModal();
        AlarmsModal alarmsModal = new AlarmsModal(this.driver);
        alarmsModal.waitForAlarmsModalLabel();
        Assert.assertTrue(alarmsModal.isAlarmsModalLabelDisplayed(), "Alarms modal is open.");
        Assert.assertTrue(alarmsModal.isAlarmActive(alarm), "Alarm is displayed as active in UI.");

        // Backend
        Assert.assertTrue(Scriptlets.isAlarmActiveInApi(alarm), "Alarm is displayed as active in API");

        // Disable the alarm
        Scriptlets.disableAlarm(alarm);

        // Recheck with alarm disabled
        this.driverUtils.navigateHome();
        homePage.waitForPageRootToBeDisplayed();
        Assert.assertTrue(homePage.isPageRootDisplayed(), "Application is running.");

        homePage.clickAlarmsModal();
        alarmsModal.waitForAlarmsModalLabel();
        Assert.assertTrue(alarmsModal.isAlarmsModalLabelDisplayed(), "Alarms modal is open.");
        Assert.assertFalse(alarmsModal.isAlarmActive(alarm), "Alarm is no longer displayed as active in UI.");

        // Backend
        Assert.assertFalse(
                Scriptlets.isAlarmActiveInApi(alarm),
                "Alarm is no longer displayed as active in API"
        );
    }
}
