package utilities;

import enums.Script;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class Runner {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USR_DIR = System.getProperty("user.dir") + File.separator;

    private Process process;

    public void copyFileToApp(String source, String destination) {
        source = USR_DIR + source;
        runProcess(String.format(Script.COPY_FILE_TO_APP.get(), source, destination));
    }

    public void restartNetdataContainer() {
        runProcess(Script.RESTART_CONTAINER.get());
    }

    private void runProcess(String script) {
        try {
            this.process = Runtime.getRuntime().exec(script);
            this.process.waitFor();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, String.format("Could not execute %s script.", script));
        } finally {
            destroyProcess();
        }
    }

    private void destroyProcess() {
        if (this.process != null) {
            this.process.destroy();
        }
    }
}
