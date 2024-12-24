package com.app.banking_system.api.users.banking_app_api_users.ui.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserResponseModel {

    private int customerId;
    private String userName;
    private String email;
    private String role;

}
