package com.car.rental.project.repository;

import com.car.rental.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value="SELECT * FROM USER U JOIN USER_ROLES UR ON U.ID = UR.USERS_ID JOIN ROLE R ON UR.ROLES_ID = R.ID WHERE R.NAME = 'ROLE_USER'", nativeQuery = true)
    List<User> findUsers();
}