package com.conferences.repository;

import com.conferences.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Integer> {

    Role findByTitle(String title);
}
