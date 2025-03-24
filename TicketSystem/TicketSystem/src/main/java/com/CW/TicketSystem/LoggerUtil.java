package com.CW.TicketSystem;
import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    private static Logger logger;

    private LoggerUtil() {}

    public static Logger getLogger(String className) {
        if (logger == null) {
            synchronized (LoggerUtil.class) {
                if (logger == null) {
                    logger = Logger.getLogger(className);
                    configureLogger();
                }
            }
        }
        return logger;
    }

    private static void configureLogger() {
        try {
            Logger rootLogger = Logger.getLogger("");
            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);

            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Logger setup failed: " + e.getMessage());
        }
    }
}

