package com.conferences.repository;

import com.conferences.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 *     Defines methods to work with roles table in database
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IRoleRepository extends JpaRepository<Role, Integer> {

    /**
     * <p>Extracts role by title</p>
     * @param title title to extract role by
     * @return extracted {@link Role} object of null if role was not found
     */
    Role findByTitle(String title);
}
