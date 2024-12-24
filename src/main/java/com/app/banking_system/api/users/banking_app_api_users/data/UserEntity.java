package com.app.banking_system.api.users.banking_app_api_users.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    @Column(nullable=false, length=50)
    private String userName;
    @Column(nullable = false)
    private String encryptedPassword;
    @Column(nullable=false, length=120, unique = true)
    private String email;
    private String role;
}
