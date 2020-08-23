package dev.rajlakshmi.userservice.controller;

import dev.rajlakshmi.userservice.dto.ResponseDto;
import dev.rajlakshmi.userservice.dto.UserDto;
import dev.rajlakshmi.userservice.dto.UserResponseDto;
import dev.rajlakshmi.userservice.model.User;
import dev.rajlakshmi.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
//@Controller - It expects that it will get the name of the HTML view/page to display as view
//@RestController - Allows to send JSON/ XML
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseDto<?> registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            List<String> errorMessages = new ArrayList<String>();
            for(ObjectError e : errors) {
                errorMessages.add(e.getDefaultMessage());
            }
            return new ResponseDto<List<String>>(HttpStatus.BAD_REQUEST, errorMessages);
        }
        User user = userService.registerUser(userDto);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullName(), user.getEmail(), user.isActive())
        );
    }

    // /user/confirm?token=34445355
    @GetMapping("/user/confirm")
    public ResponseDto<UserResponseDto> validateUser(@RequestParam String token) {
        userService.validateUser(token);

        return null;
    }

}
