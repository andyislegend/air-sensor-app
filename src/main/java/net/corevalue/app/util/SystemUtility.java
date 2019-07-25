package net.corevalue.app.util;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class SystemUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtility.class);
    private static final int DEFAULT_ERROR_CODE = -1;

    public void emergencySystemShutdown(int errorCode, String message, Throwable error) {
        LOGGER.error("Emergency system shutdown: " + message + " " + error);
        System.exit(errorCode);
    }

    public void emergencySystemShutdown(String message, Throwable error) {
        emergencySystemShutdown(DEFAULT_ERROR_CODE, message, error);
    }
}
