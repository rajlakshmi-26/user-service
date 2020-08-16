package dev.rajlakshmi.userservice.dto;

import dev.rajlakshmi.userservice.validator.EmailConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {

    @NotEmpty
    @Size(min = 5)
    private String fullName;

    // TODO: Implement custom validator for email
    @EmailConstraint
    private String email;

    // TODO: Implement custom validator for password
    @NotEmpty
    @Size(min = 6)
    private String password;

}

//Behind the scene UserDto object will be created
//Spring will receive network res
//userDto = new UserDto;
//userDto.setFullName();