package com.finitor.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finitor.model.IncomeEntity;
import java.util.List;
import java.util.UUID;


public interface  IncomeRepository extends JpaRepository<IncomeEntity, UUID> {
    @Query("SELECT e FROM IncomeEntity e WHERE e.user.id = :userId")
    List<IncomeEntity> findUserById(@Param("userId") UUID userId);
    
}
