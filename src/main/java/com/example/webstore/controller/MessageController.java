package com.example.webstore.controller;

import com.example.webstore.dto.MessageDTO;
import com.example.webstore.exception.NotFoundException;
import com.example.webstore.model.User;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.MessageService;
import com.example.webstore.model.Message;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@Tag(name = "Message Controller", description = "API для управления переписками")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/sender/{senderId}/recipient/{recipientId}")
    public ResponseEntity<List<MessageDTO>> getMessagesBetweenUsers(@PathVariable Long senderId, @PathVariable Long recipientId) {
        List<MessageDTO> messages = messageService.getMessagesBetweenUsers(senderId, recipientId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/sender/{senderId}/recipient/{recipientId}")
    public ResponseEntity<MessageDTO> sendMessage(@PathVariable Long senderId, @PathVariable Long recipientId, @RequestBody String text) {
        MessageDTO message = messageService.sendMessage(senderId, recipientId, text);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<MessageDTO>> getMessagesBySenderId(@PathVariable Long senderId) {
        List<MessageDTO> messages = messageService.getMessagesBySenderId(senderId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByRecipientId(@PathVariable Long recipientId) {
        List<MessageDTO> messages = messageService.getMessagesByRecipientId(recipientId);
        return ResponseEntity.ok(messages);
    }
}

