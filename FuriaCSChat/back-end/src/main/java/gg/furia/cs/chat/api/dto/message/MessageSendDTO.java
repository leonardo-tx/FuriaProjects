package gg.furia.cs.chat.api.dto.message;

import gg.furia.cs.chat.core.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendDTO {
    private String text;

    public Message convertToEntity() {
        return Message.builder()
                .text(text)
                .build();
    }
}
