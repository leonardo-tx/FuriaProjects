package gg.furia.cs.chat.api.dto.message;

import gg.furia.cs.chat.core.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageViewDTO {
    private Long id;
    private String text;
    private Instant createdAt;
    private Long userId;

    public static MessageViewDTO parse(Message message) {
        return new MessageViewDTO(message.getId(), message.getText(), message.getCreatedAt(), message.getUser().getId());
    }
}
