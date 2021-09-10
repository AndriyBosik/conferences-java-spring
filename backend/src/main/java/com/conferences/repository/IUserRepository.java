package com.conferences.repository;

import com.conferences.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);
}
