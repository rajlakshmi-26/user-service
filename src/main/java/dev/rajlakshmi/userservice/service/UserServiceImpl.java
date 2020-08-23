package dev.rajlakshmi.userservice.service;

import dev.rajlakshmi.userservice.dto.UserDto;
import dev.rajlakshmi.userservice.event.SuccessfulRegistrationEvent;
import dev.rajlakshmi.userservice.model.User;
import dev.rajlakshmi.userservice.model.VerificationToken;
import dev.rajlakshmi.userservice.repository.UserRepository;
import dev.rajlakshmi.userservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service //This is a bean, make sure you create this bean when application starts - Service meaning to Spring
@Transactional //Give all the transactional properties similar to the DB to this service (like rollbacks and all)
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    //We don't need to have our own publisher class


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


        //A very brute force way ->
        //TODO : Send email to user
        //TODO: Allocate resources for User

        //Problem - As soon as user registers its not necessary to allocate resources for it
        //Might happen that User registration fails- like verification token sent fails
        //In that case, we don't have to allocate resources and all.

        //Publish an event
        //Need a publisher/ producer

        applicationEventPublisher.publishEvent(
                new SuccessfulRegistrationEvent(savedUser)
        );

        //Now we do the send email and other tasks in the listener
        return savedUser;
    }

    @Override
    public User validateUser(String token) {

        //TODO : Check token repo if this token exists or not
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if(verificationToken == null) {
            //token does not exist in the repo
            return null;
        }
        //TODO: check if token is expired or not

        if(verificationToken.getExpiryTime().getTime() > new Date().getTime()) {
            // token is not yet expired
            User verifiedUser = verificationToken.getUser();

            verifiedUser.setActive(true);

            userRepository.save(verifiedUser);

            verificationTokenRepository.delete(verificationToken);

            return verifiedUser;
        }
        else {
            return null;
        }

    }


}

//Spring - bag. put all the classes you need (repo, service, security)
//Anywhere in the application we can autowire this class and spring will look for this class in the bag and autowire it
//This is AoP



//Successful User registration is an event

//We need to process the event