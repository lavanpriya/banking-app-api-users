package com.app.banking_system.api.users.banking_app_api_users.service;

import com.app.banking_system.api.users.banking_app_api_users.ui.model.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {

    UserDto registerUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String userName);
}
