package com.conferences.repository;

import com.conferences.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface IUserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

    @Query(value =
        "SELECT u FROM User u JOIN FETCH u.role WHERE u.role.title=:role"
    )
    Set<User> findAllByRole(@Param("role") String role);
}
