package com.example.webstore.service;

import com.example.webstore.dto.ProductDTO;
import com.example.webstore.dto.UserDTO;
import com.example.webstore.exception.UserException;
import com.example.webstore.exception.UserNotFoundException;
import com.example.webstore.model.Product;
import com.example.webstore.model.User;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.response.UserResponse;
import com.example.webstore.service.Impl.ImplUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements ImplUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Map<String, Object> createUser(User user) {
        Map<String, Object> response = new HashMap<>();
        boolean isUpdate = user.getUserId() != null;

        Optional<User> existingUserOptional = isUpdate
                ? userRepository.findByUserIdAndDeleteIsNull(user.getUserId())
                : userRepository.findByEmailAndDeleteIsNull(user.getEmail());

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            if (isUpdate) {
                if (!existingUser.getEmail().equals(user.getEmail()) && userRepository.findByEmailAndDeleteIsNull(user.getEmail()).isPresent()) {
                    throw new UserException("Email already exists", HttpStatus.BAD_REQUEST);
                }
                if (!existingUser.getLogin().equals(user.getLogin()) && userRepository.findByLoginAndDeleteIsNull(user.getLogin()).isPresent()) {
                    throw new UserException("Login already exists", HttpStatus.BAD_REQUEST);
                }
                updateExistingUser(existingUser, user);
                userRepository.save(existingUser);
                response.put("error", 0);
                response.put("change", true);
                response.put("description", "The data has been changed");
            } else {
                throw new UserException("Email already exists", HttpStatus.BAD_REQUEST);
            }
        } else {
            if (userRepository.findByEmailAndDeleteIsNull(user.getEmail()).isPresent()) {
                throw new UserException("Email already exists", HttpStatus.BAD_REQUEST);
            }
            if (userRepository.findByLoginAndDeleteIsNull(user.getLogin()).isPresent()) {
                throw new UserException("Login already exists", HttpStatus.BAD_REQUEST);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            response.put("error", 0);
            response.put("change", false);
            response.put("description", "Created new user");
        }
        return response;
    }

    private void updateExistingUser(User existingUser, User newUser) {
        existingUser.setFirstname(newUser.getFirstname());
        existingUser.setLastname(newUser.getLastname());
        existingUser.setRole(newUser.getRole());
        existingUser.setLogin(newUser.getLogin());
        existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        existingUser.setEmail(newUser.getEmail());
    }

    @Override
    public UserResponse getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return new UserResponse(0, "User found", true, user);
    }

    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new UserException("Error retrieving users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<UserDTO> getAvailableUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .filter(user -> user.getDelete() == null)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new UserException("Error retrieving available users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmailAndDeleteIsNull(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email " + email));
        return new UserResponse(0, "User found", true, user);
    }

    @Override
    @Transactional
    public UserResponse deleteUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        if (user.getDelete() != null) {
            return new UserResponse(1, "User already deleted", false, null);
        }

        user.setDelete(LocalDateTime.now());
        userRepository.save(user);
        return new UserResponse(0, "User deleted", true, user);
    }

    private UserDTO convertToDTO(User user) {
        List<ProductDTO> productDTOs = user.getProducts().stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());

        return new UserDTO(
                user.getUserId(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole(),
                user.getLogin(),
                user.getEmail(),
                user.getDate_create(),
                productDTOs
        );
    }

    private ProductDTO convertToProductDTO(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCount(),
                product.getCategory()
        );
    }

}
