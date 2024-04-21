package framexteam.wolframx.chat.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatMessage {

    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private MessageType status;

}
