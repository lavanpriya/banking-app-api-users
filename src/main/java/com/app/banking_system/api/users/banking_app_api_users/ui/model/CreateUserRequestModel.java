package com.app.banking_system.api.users.banking_app_api_users.ui.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestModel {
    @NotNull(message="User Name cannot be null")
    @Size(min=2, message="User Name must not be less than two characters")
    private String userName;
    @NotNull(message="Password cannot be null")
    @Size(min=8,max=16, message="Password must not be equal or greater than 8 characters and less than 16 characters")
    private String password;

    @NotNull(message="Email cannot be null")
    private String email;
    private String role;
}
