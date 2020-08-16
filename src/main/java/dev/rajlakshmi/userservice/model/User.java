package dev.rajlakshmi.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity //We want to map User class completely to a Users Table
@Table(name = "users") //If we want to specify a different name for the table we can specify it here
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //IDENTITY means that whatever id the DB is providing when I insert a row into DB, just keep that value here
    //Rely upon DB to get the Id
    private Long id;

    private String fullName;

    private String password;

    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    //LAZY - won't fetch roles, till the time you ask for it
    //default - LAZY
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") ,
                            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    //TODO : validate email
    private String email;
}
