package com.conferences.repository;

import com.conferences.entity.User;
import com.conferences.entity.projection.IUserPublicData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * <p>
 *     Defines methods to work with users table in database
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
public interface IUserRepository extends JpaRepository<User, Integer> {

    /**
     * <p>Extracts user by login</p>
     * @param login login to extract user by
     * @return extracted instance of {@link User} class
     */
    User findByLogin(String login);

    /**
     * <p>Extracts user by login or email</p>
     * @param login login to extract user by
     * @param email email to extract user by
     * @return extracted instance of {@link User} class
     */
    User findByLoginOrEmail(String login, String email);

    /**
     * <p>Extracts all users by role</p>
     * @param role role to extract users by
     * @return set of {@link User} objects
     */
    @Query(value =
        "SELECT u FROM User u JOIN FETCH u.role WHERE u.role.title=:role"
    )
    Set<User> findAllByRole(@Param("role") String role);

    /**
     * <p>Extracts speakers which are available for some topic</p>
     * @param topicId id of topic
     * @return set of {@link User} objects
     */
    @Query(nativeQuery = true, value =
        "SELECT " +
            "users.* " +
        "FROM users " +
        "WHERE NOT EXISTS (" +
            "SELECT NULL FROM moderator_proposals mp WHERE mp.speaker_id=users.id AND mp.report_topic_id=:topicId" +
        ") AND EXISTS (" +
            "SELECT NULL FROM roles r WHERE r.id=users.role_id AND r.title='speaker'" +
        ")" +
        "ORDER BY users.id"
    )
    Set<User> findAvailableSpeakersByTopic(@Param("topicId") int topicId);

    /**
     * <p>Extracts speakers which proposed themselves to some topic</p>
     * @param topicId id of topic
     * @return set of {@link IUserPublicData} objects
     */
    @Query(nativeQuery = true, value=
        "SELECT " +
            "u.id AS id," +
            "u.name AS name," +
            "u.surname AS surname," +
            "u.image_path AS imagePath " +
        "FROM speaker_proposals LEFT JOIN users u ON speaker_proposals.speaker_id=u.id WHERE speaker_proposals.report_topic_id=:topicId"
    )
    Set<IUserPublicData> findProposedSpeakersForTopic(@Param("topicId") int topicId);

    /**
     * <p>Extracts user's email by user's login</p>
     * @param login user's login to extract email by
     * @return user's email
     */
    @Query(nativeQuery = true, value = "SELECT u.email FROM users u WHERE u.login=:login")
    String findEmailByUserLogin(@Param("login") String login);

    /**
     * <p>Updates user's avatar's path</p>
     * @param login user's login to update avatar's path by
     * @param imagePath new avatar's path
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE User SET imagePath=:imagePath WHERE login=:login")
    void updateImagePath(@Param("login") String login, @Param("imagePath") String imagePath);
}
