package com.rentalcar.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String password;
    private String fullName;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, String role, String password, String fullName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        this.fullName = fullName;
    }
}
