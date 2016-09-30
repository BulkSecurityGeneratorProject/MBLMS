package com.mblearning.mblms.repository;

import com.mblearning.mblms.domain.Classes;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Classes entity.
 */
@SuppressWarnings("unused")
public interface ClassesRepository extends JpaRepository<Classes,Long> {

    @Query("select distinct classes from Classes classes left join fetch classes.users")
    List<Classes> findAllWithEagerRelationships();

    @Query("select classes from Classes classes left join fetch classes.users where classes.id =:id")
    Classes findOneWithEagerRelationships(@Param("id") Long id);

}
