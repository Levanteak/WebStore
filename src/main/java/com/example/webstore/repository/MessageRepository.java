package com.example.webstore.repository;

import com.example.webstore.model.Message;
import com.example.webstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndRecipient(User sender, User recipient);
}
