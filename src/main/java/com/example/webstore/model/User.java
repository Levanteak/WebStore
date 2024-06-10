package com.example.webstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String role;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String email;

    @JsonIgnore
    private LocalDateTime delete;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Basket> baskets;
    @Column(name = "date_create", nullable = false, updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    LocalDateTime date_create;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @JsonBackReference
    private User recipient;



}