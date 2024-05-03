package framexteam.wolframx.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import framexteam.wolframx.chat.model.ChatMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class ChatController {

    private static final Logger logger = LogManager.getLogger(ChatController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatMessage receiveMessage(@Payload ChatMessage message){
        logger.info("Received public message from {}: {}", message.getSenderName(), message.getMessage()); 
        return message;
    }

    @MessageMapping("/private-message")
    public ChatMessage recMessage(@Payload ChatMessage message){
        logger.info("Received private message from {} to {}: {}", 
            message.getSenderName(), message.getReceiverName(), message.getMessage());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        return message;
    }
}