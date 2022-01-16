package utilities;

import alarms.Alarm;
import enums.ApiEndpoint;
import org.json.JSONException;
import org.json.JSONObject;

public class Scriptlets {

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
}
