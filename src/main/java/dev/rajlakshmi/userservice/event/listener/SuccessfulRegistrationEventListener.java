package dev.rajlakshmi.userservice.event.listener;

import dev.rajlakshmi.userservice.event.SuccessfulRegistrationEvent;
import dev.rajlakshmi.userservice.model.User;
import dev.rajlakshmi.userservice.model.VerificationToken;
import dev.rajlakshmi.userservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SuccessfulRegistrationEventListener implements ApplicationListener<SuccessfulRegistrationEvent> {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Override
    public void onApplicationEvent(SuccessfulRegistrationEvent successfulRegistrationEvent) {
        User registeredUser = successfulRegistrationEvent.getRegisteredUser();

        VerificationToken verificationToken = new VerificationToken(registeredUser);

        verificationTokenRepository.save(verificationToken);

        //TODO : Send email to registered user

        //email contains verification token

        //TODO: Allocate resources for User
    }

}
