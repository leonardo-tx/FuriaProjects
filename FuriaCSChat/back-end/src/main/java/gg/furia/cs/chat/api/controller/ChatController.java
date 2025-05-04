package gg.furia.cs.chat.api.controller;

import gg.furia.cs.chat.api.dto.ApiResponse;
import gg.furia.cs.chat.api.dto.message.MessageSendDTO;
import gg.furia.cs.chat.api.dto.message.MessageViewDTO;
import gg.furia.cs.chat.core.decorators.NeedsUserContext;
import gg.furia.cs.chat.core.entity.Message;
import gg.furia.cs.chat.core.service.MessageService;
import gg.furia.cs.chat.core.service.UserService;
import gg.furia.cs.chat.core.utils.exception.FuriaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final MessageService messageService;
    private final UserService userService;

    @NeedsUserContext
    @PostMapping
    public ResponseEntity<ApiResponse<MessageViewDTO>> sendMessage(
            @RequestBody MessageSendDTO messageSendDTO
    ) throws FuriaException {
        Message message = messageSendDTO.convertToEntity();
        Message createdMessage = messageService.sendMessage(
                message,
                userService.getContext().getUserOrThrow()
        );
        return ApiResponse.success(MessageViewDTO.parse(createdMessage), HttpStatus.CREATED).createResponseEntity();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MessageViewDTO>>> getAllMessages() {
        List<MessageViewDTO> messages = messageService.getAllMessages()
                .stream()
                .map(MessageViewDTO::parse)
                .toList();
        return ApiResponse.success(messages, HttpStatus.OK).createResponseEntity();
    }
}
