package com.mblearning.mblms.repository;

import com.mblearning.mblms.domain.UserProperties;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the UserProperties entity.
 */
@SuppressWarnings("unused")
public interface UserPropertiesRepository extends JpaRepository<UserProperties,Long> {

    UserProperties findOneByUserLogin(String login);
    UserProperties findOneByUserEmail(String email);

}
