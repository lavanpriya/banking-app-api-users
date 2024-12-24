package com.app.banking_system.api.users.banking_app_api_users.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private int customerId;
    private String userName;
    private String password;
    private String encryptedPassword;
    private String email;
    private String role;
}
