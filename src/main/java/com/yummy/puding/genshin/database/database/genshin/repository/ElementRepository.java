package com.yummy.puding.genshin.database.database.genshin.repository;

import com.yummy.puding.genshin.database.database.genshin.model.dbi.ElementDbi;
import com.yummy.puding.genshin.database.entity.ElementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ElementRepository extends JpaRepository<ElementEntity, UUID> {

    @Query(value = "SELECT ID, NAME FROM ELEMENTS", nativeQuery = true)
    public List<ElementDbi> selectAll();

}
