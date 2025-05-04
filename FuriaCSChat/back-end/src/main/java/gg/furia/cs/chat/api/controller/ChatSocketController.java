package gg.furia.cs.chat.api.controller;

import gg.furia.cs.chat.api.dto.message.MessageViewDTO;
import gg.furia.cs.chat.core.event.MessageUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void handleMessageUpdateEvent(MessageUpdateEvent event) {
        MessageViewDTO response = MessageViewDTO.parse(event.getMessage());
        simpMessagingTemplate.convertAndSend("/receiver/chat", response);
    }
}
