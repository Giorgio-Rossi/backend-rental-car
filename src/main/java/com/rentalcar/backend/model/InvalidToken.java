package com.rentalcar.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.Date;

@Getter
@Entity
@Table(name = "invalid_tokens")
public class InvalidToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Date expiration;

    public InvalidToken() {
    }

    public InvalidToken(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}
