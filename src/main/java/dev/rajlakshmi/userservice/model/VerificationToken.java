package dev.rajlakshmi.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class VerificationToken {

    private static final int VALIDITY_TIME = 4 * 60; //in minutes
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class)
    private User user;
    //One user will have one token at a time
    //If token expires, the token will be updated

    private Date expiryTime;

    public VerificationToken() {
        super();
    }

    public VerificationToken(User user) {
        String token = generateRandomUniqueToken();

        this.token = token;
        this.user = user;

        this.expiryTime = calculateExpiryTime();
    }

    public void updateToken() {

        this.token = generateRandomUniqueToken();
        this.expiryTime = calculateExpiryTime();

        //Generate new token
        //Calculate new expiry time
    }

    private String generateRandomUniqueToken() {
        return UUID.randomUUID().toString();
        //Universal unique ID
    }

    private Date calculateExpiryTime() {

        Calendar calendar = Calendar.getInstance();

        Date currentDateAndTime = new Date();

        calendar.setTimeInMillis(currentDateAndTime.getTime());
        calendar.add(Calendar.MINUTE, VALIDITY_TIME);

        return new Date(calendar.getTime().getTime());

        //calendar.getTime() - instance of Date class
    }
}

//We need to keep the token stored in a DB so that we can verify against valid user
//This verification token is only needed once -> valid only till a user registers & verifies itself
//DB -> userId, token, expiryTime


//scaler.com
//create account
//get email to verify - click link
//scaler.com/verify/{token}
//click on link - this token must be saved in db so that we are sure that this user has to click this token link
