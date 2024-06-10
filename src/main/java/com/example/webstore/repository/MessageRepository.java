package com.example.webstore.repository;

import com.example.webstore.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderUserIdAndRecipientUserId(Long senderId, Long recipientId);
    List<Message> findBySenderUserId(Long senderId);
    List<Message> findByRecipientUserId(Long recipientId);

}
