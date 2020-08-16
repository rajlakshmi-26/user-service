package dev.rajlakshmi.userservice.repository;

import dev.rajlakshmi.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //Behind the scene, this method is already implemented
    User findByEmail(String email);
}
