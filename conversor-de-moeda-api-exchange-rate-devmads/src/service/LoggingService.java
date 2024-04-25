package service;

public class LoggingService {
    private JsonLogger jsonLogger;

    public LoggingService() {
        this.jsonLogger = new JsonLogger();
    }

    public void logData(String data) {
        jsonLogger.logOperation(data);
    }
}
