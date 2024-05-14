package framexteam.wolframx.chat.model;

import lombok.*;

import jakarta.persistence.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "chat_messages") 
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private String date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MessageType status;

    @Column(name = "timestamp")
    @Convert(converter = LogicalTimestampConverter.class)
    private LogicalTimestamp timestamp;

    @Converter
    public static class LogicalTimestampConverter implements AttributeConverter<LogicalTimestamp, String> {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(LogicalTimestamp attribute) {
            try {
                return objectMapper.writeValueAsString(attribute.getActiveVector()); 
            } catch (IOException e) {
                throw new RuntimeException("Error converting LogicalTimestamp to JSON", e);
            }
        }

        @Override
        public LogicalTimestamp convertToEntityAttribute(String dbData) {
            try {
                return new LogicalTimestamp(objectMapper.readValue(dbData, new TypeReference<HashMap<String, Integer>>() {}));
            } catch (IOException e) {
                throw new RuntimeException("Error converting JSON to LogicalTimestamp", e);
            }
        }
    }
}