package enums;

public enum Script {

    COPY_FILE_TO_APP("docker cp %s netdata:%s"),
    RESTART_CONTAINER("docker restart netdata");

    private final String value;

    Script(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
