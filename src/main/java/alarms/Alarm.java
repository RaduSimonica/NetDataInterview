package alarms;

import alarms.enums.Chart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.config.ConfigData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alarm {

    private static final Logger LOGGER = LogManager.getLogger();

    @Getter
    private String name;

    @Getter
    private Chart chart;

    @Getter
    private Lookup lookup;

    @Getter
    private String units;

    @Getter
    private String frequency;

    @Getter
    private String warning;

    @Getter
    private String critical;

    @Getter
    private String info;

    private List<String> list;

    private void buildList() {
        this.list = new ArrayList<>();
        this.list.add(String.format("alarm: %s", this.getName()));
        this.list.add(String.format("on: %s", this.getChart().get()));
        this.list.add(
                String.format(
                        "lookup: %s %s %s %s",
                        this.getLookup().getMetrics(),
                        this.getLookup().getDuration(),
                        this.getLookup().getMethod(),
                        this.getLookup().getDimension()
                )
        );
        this.list.add(String.format("units: %s", this.getUnits()));
        this.list.add(String.format("every: %s", this.getFrequency()));
        this.list.add(String.format("warn: %s", this.getWarning()));
        this.list.add(String.format("crit: %s", this.getCritical()));
        this.list.add(String.format("info: %s", this.getInfo()));
    }

    public List<String> getAsList() {
        buildList();
        return this.list;
    }

    public File buildAsFile() {
        buildList();
        String path = ConfigData.getAlarmTmpDir() +
                File.separator +
                this.name +
                ".conf";

        File alarmFile = new File(path);

        try (FileOutputStream stream = new FileOutputStream(alarmFile)){
            for (String line : this.list) {
                stream.write(line.getBytes(StandardCharsets.UTF_8));
                stream.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Could not write alarm to file.");
        }

        return alarmFile;
    }
}
