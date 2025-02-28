package com.rentalcar.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String role ;

    @Column(nullable = false)
    private String password;

    private String fullName;


    public User() {
    }

    public User(Long id, String username, String email, String role, String password, String fullName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        this.fullName = fullName;
    }
}
