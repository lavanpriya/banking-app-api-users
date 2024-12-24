package com.app.banking_system.api.users.banking_app_api_users.ui.controllers;

import com.app.banking_system.api.users.banking_app_api_users.service.UsersService;
import com.app.banking_system.api.users.banking_app_api_users.ui.model.CreateUserRequestModel;
import com.app.banking_system.api.users.banking_app_api_users.ui.model.CreateUserResponseModel;
import com.app.banking_system.api.users.banking_app_api_users.ui.model.UserDto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;
    @Autowired
    private UsersService usersService;

    @GetMapping("status")
    public String status(){
        return "working  on port  "+ env.getProperty("local.server.port") + env.getProperty("token.secret");
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> registerUser(@Valid @RequestBody CreateUserRequestModel userDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto returnValue = usersService.registerUser(userDto);
        CreateUserResponseModel responseModel = modelMapper.map(returnValue, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }
}
