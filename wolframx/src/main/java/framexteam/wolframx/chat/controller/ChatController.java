package framexteam.wolframx.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import framexteam.wolframx.chat.model.ChatMessage;
import framexteam.wolframx.chat.model.LogicalTimestamp;
import framexteam.wolframx.chat.model.MessageType;
import framexteam.wolframx.chat.repository.MessageRepository;
import framexteam.wolframx.chat.service.LogicalClockService;

import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class ChatController {

    private static final Logger logger = LogManager.getLogger(ChatController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private LogicalClockService logicalClockService;

    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatMessage receiveMessage(@Payload ChatMessage message){
        logger.info("Received public message from {}: {}", message.getSenderName(), message.getMessage()); 
    
        message.setTimestamp(logicalClockService.getLogicalTimestamp(message.getSenderName()));
    
        logicalClockService.incrementClock(message.getSenderName());
    
        logicalClockService.addHistoryEntry(message.getSenderName(), message.getTimestamp(), message, MessageType.MESSAGE);

        messageRepository.save(message);
    
        return message;
    }

    @MessageMapping("/private-message")
    public ChatMessage recMessage(@Payload ChatMessage message){
        logger.info("Received private message from {} to {}: {}", 
            message.getSenderName(), message.getReceiverName(), message.getMessage());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        return message;
    }

    @MessageMapping("/join")
    public void handleJoin(@Payload String username) {
        LogicalTimestamp timestamp = logicalClockService.getLogicalTimestamp(username);

        if (!logicalClockService.isUserExists(username)) { 
            timestamp.setTimestamp(username, 0); 
            logicalClockService.addUser(username);
        } else {
            int lastTimestamp = logicalClockService.getLastTimestampFromHistory(username);
            timestamp.setTimestamp(username, lastTimestamp);
        }

        // Создать ChatMessage для события JOIN
        ChatMessage joinMessage = ChatMessage.builder()
                .senderName(username)
                .status(MessageType.JOIN)
                .timestamp(timestamp)
                .build();

        logicalClockService.addHistoryEntry(username, timestamp, joinMessage, MessageType.JOIN);
        logicalClockService.incrementClock(username); 

        // Отправка истории сообщений
        List<ChatMessage> messages = logicalClockService.getHistory(); // Извлекаем из базы данных

        simpMessagingTemplate.convertAndSendToUser(username, "/history", messages); 
    }

    @MessageMapping("/leave") 
    public void handleLeave(@Payload String username) {
        logicalClockService.incrementClock(username);

        // Создать ChatMessage для события LEAVE
        ChatMessage leaveMessage = ChatMessage.builder()
                .senderName(username)
                .status(MessageType.LEAVE)
                .timestamp(logicalClockService.getLogicalTimestamp(username))
                .build();

        logicalClockService.addHistoryEntry(username, new LogicalTimestamp(new HashMap<>()), leaveMessage, MessageType.LEAVE);
        logicalClockService.removeUser(username);
        logicalClockService.removeLastUserEntry(username);
    }
}