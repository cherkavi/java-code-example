package com.cherkashyn.vitalii.tools.usermanager.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessRepository extends CrudRepository<com.cherkashyn.vitalii.tools.usermanager.domain.Process, Integer> {
    Optional<com.cherkashyn.vitalii.tools.usermanager.domain.Process> findByName(String name);
}
