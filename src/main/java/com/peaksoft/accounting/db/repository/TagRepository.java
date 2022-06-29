package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.api.payload.TagResponse;
import com.peaksoft.accounting.db.entity.TagEntity;
import com.peaksoft.accounting.service.TagService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByNameTag(String tagName);

    @Override
    List<TagEntity> findAll();

    @Override
    <S extends TagEntity> S save(S entity);

    @Override
    Optional<TagEntity> findById(Long aLong);

    @Override
    void deleteById(Long aLong);
    @Query("select t from TagEntity t where t.nameTag like concat(:tagName,'%') and t.company.company_id=:companyId")
    List<TagEntity> searchAllByNameTag(String tagName,Long companyId);

    @Query("select t from TagEntity t where t.company.company_id=:companyId")
    List<TagEntity> getAll(Long companyId);

    @Query("select t from TagEntity t where t.company.company_id=:companyId")
    List<TagEntity> findAllByCompany(Long companyId);
}