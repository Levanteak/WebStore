package com.example.webstore.controller;

import com.example.webstore.exception.NotFoundException;
import com.example.webstore.model.User;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.MessageService;
import com.example.webstore.model.Message;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final UserRepository userRepository;

    public MessageController(MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable Long senderId, @PathVariable Long recipientId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new NotFoundException("Sender not found"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new NotFoundException("Recipient not found"));
        List<Message> messages = messageService.getMessagesBetweenUsers(sender, recipient);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/{senderId}/{recipientId}")
    public ResponseEntity<Message> sendMessage(@PathVariable Long senderId, @PathVariable Long recipientId, @RequestBody String text) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new NotFoundException("Sender not found"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new NotFoundException("Recipient not found"));
        Message message = messageService.sendMessage(sender, recipient, text);
        return ResponseEntity.ok(message);
    }
}

