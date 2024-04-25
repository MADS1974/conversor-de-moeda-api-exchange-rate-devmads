package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonLogger {
    private static final String LOG_FILE_PATH = "Digite aqui o caminho para a pasta logs";


    public static void logOperation(String operation) {
        try {
            List<String> logList = readLog();
            logList.add(operation);
            writeLog(logList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readLog() throws IOException {
        File logFile = new File(LOG_FILE_PATH);
        if (!logFile.exists()) {
            logFile.createNewFile();
            return new ArrayList<>();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, ArrayList.class);
        }
    }

    private static void writeLog(List<String> logList) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(logList);
            writer.write(json);
        }
    }
}
