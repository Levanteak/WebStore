package com.example.webstore.service.Impl;

import com.example.webstore.dto.MessageDTO;

import java.util.List;

public interface ImplMessageService {
    MessageDTO sendMessage(Long senderId, Long recipientId, String text);
    List<MessageDTO> getMessagesBetweenUsers(Long senderId, Long recipientId);
    List<MessageDTO> getMessagesBySenderId(Long senderId);
    List<MessageDTO> getMessagesByRecipientId(Long recipientId);
    List<MessageDTO> getFullConversationBetweenUsers(Long senderId, Long recipientId);

}
