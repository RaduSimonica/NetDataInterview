package enums;

public enum Script {

    COPY_FILE_TO_APP("docker cp %s netdata:%s"),
    DELETE_FILE_FROM_APP("docker exec netdata rm -rf %s"),
    RESTART_CONTAINER("docker restart netdata");

    private final String value;

    Script(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
