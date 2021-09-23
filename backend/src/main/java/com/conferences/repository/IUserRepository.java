package com.conferences.repository;

import com.conferences.entity.User;
import com.conferences.entity.projection.IUserPublicData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface IUserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

    User findByLoginOrEmail(String login, String email);

    @Query(value =
        "SELECT u FROM User u JOIN FETCH u.role WHERE u.role.title=:role"
    )
    Set<User> findAllByRole(@Param("role") String role);

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

    @Query(nativeQuery = true, value=
        "SELECT " +
            "u.id AS id," +
            "u.name AS name," +
            "u.surname AS surname," +
            "u.image_path AS imagePath " +
        "FROM speaker_proposals LEFT JOIN users u ON speaker_proposals.speaker_id=u.id WHERE speaker_proposals.report_topic_id=:topicId"
    )
    Set<IUserPublicData> findProposedSpeakersForTopic(@Param("topicId") int topicId);

    @Query(nativeQuery = true, value = "SELECT u.email FROM users u WHERE u.login=:login")
    String findEmailByUserLogin(@Param("login") String login);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE User SET imagePath=:imagePath WHERE login=:login")
    void updateImagePath(@Param("login") String login, @Param("imagePath") String imagePath);
}
