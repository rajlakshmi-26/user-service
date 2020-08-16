package dev.rajlakshmi.userservice.service;

import dev.rajlakshmi.userservice.dto.UserDto;
import dev.rajlakshmi.userservice.model.User;
import dev.rajlakshmi.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service //This is a bean, make sure you create this bean when application starts - Service meaning to Spring
@Transactional //Give all the transactional properties similar to the DB to this service (like rollbacks and all)
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    @Override
    public User registerUser(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()) != null) {
            //TODO : Throw exception
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); //TODO : Encrypt Password here

        User savedUser = userRepository.save(user);
        //Here I will get the ID
        return savedUser;
    }
}

//Spring - bag. put all the classes you need (repo, service, security)
//Anywhere in the application we can autowire this class and spring will look for this class in the bag and autowire it
//This is AoP