package enums;

public enum Path {

    ALERTS("/usr/lib/netdata/conf.d/health.d/");

    private final String value;

    Path(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
