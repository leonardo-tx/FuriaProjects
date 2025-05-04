package gg.furia.cs.chat.core.service;

import gg.furia.cs.chat.core.entity.Message;
import gg.furia.cs.chat.core.entity.User;
import gg.furia.cs.chat.core.event.MessageUpdateEvent;
import gg.furia.cs.chat.core.repository.MessageRepository;
import gg.furia.cs.chat.core.utils.exception.FuriaException;
import gg.furia.cs.chat.core.utils.validator.MessageValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Message sendMessage(Message message, User user) throws FuriaException {
        MessageValidator.validateFields(message);
        message.setId(null);
        message.setUser(user);
        message.setCreatedAt(Instant.now());

        Message createdMessage = messageRepository.save(message);
        applicationEventPublisher.publishEvent(new MessageUpdateEvent(this, createdMessage));

        return createdMessage;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
