package framexteam.wolframx.chat.service;

import framexteam.wolframx.chat.model.ChatMessage;
import framexteam.wolframx.chat.model.LogicalTimestamp;
import framexteam.wolframx.chat.model.MessageType;
import framexteam.wolframx.chat.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Comparator;

@Service
public class LogicalClockService {

    @Autowired
    private MessageRepository messageRepository;

    private HashMap<String, Integer> activeVector = new HashMap<>();
    //private List<ChatMessage> history = new ArrayList<>(); // Теперь хранит ChatMessage
    private HashMap<String, ChatMessage> lastUserEntry = new HashMap<>(); // Теперь хранит ChatMessage

    public LogicalTimestamp getLogicalTimestamp(String userId) {
        return new LogicalTimestamp(activeVector);
    }

    public void incrementClock(String userId) {
        activeVector.put(userId, activeVector.getOrDefault(userId, 0) + 1);
        ChatMessage entry = lastUserEntry.get(userId);
        if (entry != null) {
            LogicalTimestamp newTimestamp = new LogicalTimestamp(new HashMap<>(activeVector));
            entry.setTimestamp(newTimestamp);
        }
    }

    public void addHistoryEntry(String userId, LogicalTimestamp timestamp, ChatMessage message, MessageType type) {
        LogicalTimestamp newTimestamp = new LogicalTimestamp(new HashMap<>(activeVector));

        message.setTimestamp(newTimestamp); 
        messageRepository.save(message); 
        lastUserEntry.put(userId, message);

        messageRepository.save(message); // Сохраняем сообщение в базе данных
        lastUserEntry.put(userId, message);
    }

    public List<ChatMessage> getHistory() {
        List<ChatMessage> messages = messageRepository.findAll(); 
        messages.sort(Comparator.comparing(ChatMessage::getTimestamp));
        return messages;
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