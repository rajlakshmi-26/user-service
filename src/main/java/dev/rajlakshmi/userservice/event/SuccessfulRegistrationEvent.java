package dev.rajlakshmi.userservice.event;

import dev.rajlakshmi.userservice.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SuccessfulRegistrationEvent extends ApplicationEvent {

    private final User registeredUser;

    public SuccessfulRegistrationEvent(User registeredUser) {
        super(registeredUser);
        this.registeredUser = registeredUser;
    }
}


//For event there might be one producer and multiple listener