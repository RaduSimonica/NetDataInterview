package enums;

public enum ApiEndpoint {

    ALARMS("api/v1/alarms");

    private final String value;

    ApiEndpoint(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
