package com.yummy.puding.genshin.database.database.genshin.repository;

import com.yummy.puding.genshin.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository(value = "UserRepositoryDatabaseGenshin")
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
