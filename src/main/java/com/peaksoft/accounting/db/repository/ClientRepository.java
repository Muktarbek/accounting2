package com.peaksoft.accounting.db.repository;

import com.peaksoft.accounting.db.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    @Override
    <S extends ClientEntity> S save(S entity);

    Optional<ClientEntity> findByCompanyName(String tagName);

    Optional<ClientEntity> findByEmail(String email);

    @Override
    Optional<ClientEntity> findById(Long aLong);

    @Override
    List<ClientEntity> findAll();

    @Override
    void deleteById(Long aLong);

    @Query("select c from ClientEntity c inner join c.tags t where (upper( c.clientName) LIKE CONCAT('%',:name,'%') " +
            "or upper(c.companyName) like concat ('%',:name,'%')or upper(c.email) like concat ('%',:name,'%')or upper(t.nameTag) like concat('%',:name,'%'))" +
            "and (t.tag_id =:tagId or :tagId is null)")
    List<ClientEntity> search(@Param("name") String name, Long tagId, Pageable pageable);

    @Query("select s from ClientEntity s ")
    Page<ClientEntity> findAllByPagination(Pageable pageable);

}