package gg.furia.cs.chat.core.event;

import gg.furia.cs.chat.core.entity.Message;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageUpdateEvent extends ApplicationEvent {
    private final Message message;

    public MessageUpdateEvent(Object source, Message message) {
        super(source);
        this.message = message;
    }
}
