package enums;

public enum Script {

    COPY_FILE_TO_APP("docker cp %s netdata:%s"),
    RESTART_CONTAINER("docker restart netdata"),
    GET_APP_IP("docker inspect --format '{{ .NetworkSettings.IPAddress }}' netdata"); // Use this manually to get application's IP address.

    private final String value;

    Script(String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
