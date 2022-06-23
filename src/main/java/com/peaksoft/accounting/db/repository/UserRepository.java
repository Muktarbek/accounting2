package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByToken(String token);

    @Query("SELECT u from UserEntity u where u.email = ?1")
    UserEntity checkByEmail(String email);

    @Override
    List<UserEntity> findAll();

    @Override
    UserEntity getById(Long aLong);

    @Override
    <S extends UserEntity> S save(S entity);

    @Override
    Optional<UserEntity> findById(Long aLong);
}
