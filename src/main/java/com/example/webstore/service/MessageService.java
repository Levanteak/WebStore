package com.example.webstore.service;

import com.example.webstore.dto.MessageDTO;
import com.example.webstore.model.User;
import com.example.webstore.repository.MessageRepository;
import com.example.webstore.model.Message;

import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.Impl.ImplMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService implements ImplMessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public MessageDTO sendMessage(Long senderId, Long recipientId, String text) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new RuntimeException("Recipient not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setText(text);
        message.setSentAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        return convertToDTO(savedMessage);
    }

    public List<MessageDTO> getMessagesBetweenUsers(Long senderId, Long recipientId) {
        List<Message> messages = messageRepository.findBySenderUserIdAndRecipientUserId(senderId, recipientId);
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public List<MessageDTO> getMessagesBySenderId(Long senderId) {
        List<Message> messages = messageRepository.findBySenderUserId(senderId);
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<MessageDTO> getMessagesByRecipientId(Long recipientId) {
        List<Message> messages = messageRepository.findByRecipientUserId(recipientId);
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public List<MessageDTO> getFullConversationBetweenUsers(Long senderId, Long recipientId) {
        List<Message> messages = messageRepository.findBySenderUserIdAndRecipientUserId(senderId, recipientId);
        messages.addAll(messageRepository.findBySenderUserIdAndRecipientUserId(recipientId, senderId));
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    private MessageDTO convertToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(message.getId());
        messageDTO.setSenderId(message.getSender().getUserId());
        messageDTO.setRecipientId(message.getRecipient().getUserId());
        messageDTO.setText(message.getText());
        messageDTO.setTimestamp(message.getSentAt());

        MessageDTO.UserSummary senderSummary = new MessageDTO.UserSummary();
        senderSummary.setUserId(message.getSender().getUserId());
        senderSummary.setLogin(message.getSender().getLogin());
        senderSummary.setEmail(message.getSender().getEmail());
        messageDTO.setSender(senderSummary);

        MessageDTO.UserSummary recipientSummary = new MessageDTO.UserSummary();
        recipientSummary.setUserId(message.getRecipient().getUserId());
        recipientSummary.setLogin(message.getRecipient().getLogin());
        recipientSummary.setEmail(message.getRecipient().getEmail());
        messageDTO.setRecipient(recipientSummary);

        return messageDTO;
    }
}