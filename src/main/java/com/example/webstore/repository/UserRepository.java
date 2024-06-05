package com.example.webstore.repository;

import com.example.webstore.model.Product;
import com.example.webstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin (String login);
    Optional<User> findByEmail(String email);

    Optional<User> findByUserIdAndDeleteIsNull(Long userId);
    Optional<User> findByEmailAndDeleteIsNull(String email);
    List<User> findByDeleteIsNull();
    Optional<User> findByLoginAndDeleteIsNull(String login);

}
