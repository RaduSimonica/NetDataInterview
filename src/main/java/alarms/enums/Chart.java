package alarms.enums;

public enum Chart {

    SYSTEM_RAM("system.ram");

    private final String value;

    Chart(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
