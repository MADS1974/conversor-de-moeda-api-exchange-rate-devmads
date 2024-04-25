package service;

import java.util.ArrayList;
import java.util.List;

public class DataHistoryService {
    public static List<String> historyList = new ArrayList<>();

    public static void addToList(String message) {
        historyList.add(message);
    }

    public static List<String> getHistoryList() {
        return historyList;
    }
}
