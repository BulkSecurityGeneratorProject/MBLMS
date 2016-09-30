package com.mblearning.mblms.repository;

import com.mblearning.mblms.domain.Profiles;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Profiles entity.
 */
@SuppressWarnings("unused")
public interface ProfilesRepository extends JpaRepository<Profiles,Long> {

    @Query("select profiles from Profiles profiles where profiles.user.login = ?#{principal.username}")
    List<Profiles> findByUserIsCurrentUser();

    @Query("select distinct profiles from Profiles profiles left join fetch profiles.classes")
    List<Profiles> findAllWithEagerRelationships();

    @Query("select profiles from Profiles profiles left join fetch profiles.classes where profiles.id =:id")
    Profiles findOneWithEagerRelationships(@Param("id") Long id);

}
