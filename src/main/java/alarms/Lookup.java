package alarms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Lookup {

    @Getter
    private String metrics;

    @Getter
    private String duration;

    @Getter
    private String method;

    @Getter
    private String dimension;
}
