package com.example.webstore.service;

import com.example.webstore.model.User;
import com.example.webstore.repository.MessageRepository;
import com.example.webstore.model.Message;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessagesBetweenUsers(User sender, User recipient) {
        return messageRepository.findBySenderAndRecipient(sender, recipient);
    }
    public Message sendMessage(User sender, User recipient, String text) {
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setText(text);
        message.setSentAt(LocalDateTime.now());
        return messageRepository.save(message);
    }
}
