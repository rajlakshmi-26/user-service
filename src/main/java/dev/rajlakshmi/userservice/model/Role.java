package dev.rajlakshmi.userservice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
                                  inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private List<Permission> permissions;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    //No need of join column here - already created in User Class
    //Specify which table by - mappedBy
    private List<User> users;
}
