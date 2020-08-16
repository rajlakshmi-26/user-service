package dev.rajlakshmi.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseDto<T> {

    private T data;
    //data will be custom user object - fullname, email etc.
    //We would not want to send password, id in the response

    private HttpStatus status;
    //For user service status will be 200 OK

    public ResponseDto(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }


}
