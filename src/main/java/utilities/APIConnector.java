package utilities;

import enums.ApiEndpoint;
import okhttp3.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import utilities.config.ConfigData;

import javax.annotation.Nullable;
import java.io.IOException;

public class APIConnector {

    private static final Logger LOGGER = LogManager.getLogger();

    private OkHttpClient client;
    private HttpUrl.Builder builder;
    private String url;
    private Request request;
    private Call call;
    private Response response;

    public APIConnector(ApiEndpoint endpoint) {
        this.client = new OkHttpClient();
        setBuilder(endpoint);
    }

    private void setBuilder(ApiEndpoint endpoint) {
        HttpUrl url = HttpUrl.parse(ConfigData.getAppHostname() + endpoint.get());
        if (url != null) {
            this.builder = url.newBuilder();
        }
    }

    public APIConnector addQueryParameter(String key, @Nullable String value) {
        this.builder.addQueryParameter(key, value);
        return this;
    }

    private void setUrl() {
        this.url = this.builder.build().toString();
    }

    private void setRequest() {
        this.request = new Request.Builder()
                .url(this.url)
                .build();
    }

    private void setCall() {
        this.call = this.client.newCall(this.request);
    }

    private void execute() {
        setUrl();
        setRequest();
        setCall();

        try {
            this.response = this.call.execute();
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Failed executing API call.", e);
        }
    }

    public JSONObject getResponseJSON() {
        execute();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response.body().string());
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Failed to parse response as JSON", e);
        }

        return jsonObject;
    }
}
