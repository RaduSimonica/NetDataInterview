package utilities;

import alarms.Alarm;
import enums.ApiEndpoint;
import enums.Path;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class Scriptlets {

    private static final Logger LOGGER = LogManager.getLogger();
    public static Boolean isAlarmActiveInApi(Alarm alarm) {
        JSONObject jsonObject = new APIConnector(ApiEndpoint.ALARMS)
                .addQueryParameter("active", "true")
                .getResponseJSON();
        try {
            JSONObject alarms = (JSONObject) jsonObject.get("alarms");
            JSONObject apiALARM = (JSONObject) alarms.get(String.format("%s.%s", alarm.getChart().get(), alarm.getName()));
            return (Boolean) apiALARM.get("active");
        } catch (JSONException e) {
            return false;
        }
    }

    public static void disableAlarm(Alarm alarm) {
        Runner runner = new Runner();
        runner.deleteFileFromApp(Path.ALERTS.get() + alarm.getAlarmFile().getName());
        runner.copyFileToApp(alarm.getAlarmFile().getPath(), Path.ALERTS.get());
        runner.restartNetdataContainer();
    }
}
