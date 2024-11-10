package com.yummy.puding.genshin.database.auth.repository;

import com.yummy.puding.genshin.database.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * FROM USERS WHERE ID = :id", nativeQuery = true)
    UserEntity findByUsername(@Param("id") String id);

}
