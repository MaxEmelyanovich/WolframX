package framexteam.wolframx.chat.service;

import framexteam.wolframx.chat.model.ChatMessage;
import framexteam.wolframx.chat.model.LogicalTimestamp;
import framexteam.wolframx.chat.model.MessageType;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Service
public class LogicalClockService {

    private HashMap<String, Integer> activeVector = new HashMap<>();
    private List<ChatMessage> history = new ArrayList<>(); 
    private HashMap<String, ChatMessage> lastUserEntry = new HashMap<>();

    public LogicalTimestamp getLogicalTimestamp(String userId) {
        return new LogicalTimestamp(activeVector);
    }

    public void incrementClock(String userId) {
        activeVector.put(userId, activeVector.getOrDefault(userId, 0) + 1);
        ChatMessage entry = lastUserEntry.get(userId);
        if (entry != null) {
            entry.setTimestamp(new LogicalTimestamp(new HashMap<>(activeVector))); // Обновляем timestamp у существующего сообщения
            lastUserEntry.put(userId, entry); // Обновляем lastUserEntry
        }
    }

    public void addHistoryEntry(String userId, LogicalTimestamp timestamp, ChatMessage message, MessageType type) {
        LogicalTimestamp newTimestamp = new LogicalTimestamp(new HashMap<>(activeVector));
        message.setTimestamp(newTimestamp); 

        history.add(message);
        lastUserEntry.put(userId, message);
    }

    public List<ChatMessage> getHistory() {
        return new ArrayList<>(history);
    }

    public void removeUser(String userId) {
        activeVector.remove(userId);
    }

    public boolean isUserExists(String userId) {
        return activeVector.containsKey(userId);
    }

    public void addUser(String userId) {
        activeVector.put(userId, 0);
    }

    public int getLastTimestampFromHistory(String userId) {
        ChatMessage entry = lastUserEntry.get(userId);
        if (entry != null) {
            return entry.getTimestamp().getTimestamp(userId);
        }
        return 0;
    }

    public LogicalTimestamp getInitialTimestamp() {
        return new LogicalTimestamp(new HashMap<>());
    }

    public void removeLastUserEntry(String userId) {
        lastUserEntry.remove(userId);
    }
}