package com.cherkashyn.vitalii.example.restjdbcstatus.service;


import com.cherkashyn.vitalii.example.restjdbcstatus.domain.DeployStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


@RepositoryRestResource(collectionResourceRel = "status", path = "status")
public interface DeployStatusRepository extends PagingAndSortingRepository<DeployStatus, Long>{

    DeployStatus findByKey(@Param("key") String key);


    @Override
    default Page<DeployStatus> findAll(Pageable pageable) {
        return findBy(pageable);
    }

    @RestResource(exported = true)
    Page<DeployStatus> findBy(Pageable pageable);
}
