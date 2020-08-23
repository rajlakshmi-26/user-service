package dev.rajlakshmi.userservice.service;

import dev.rajlakshmi.userservice.dto.UserDto;
import dev.rajlakshmi.userservice.model.User;

public interface UserService {

    public User registerUser(UserDto userDto);

    public User validateUser(String token);
}
