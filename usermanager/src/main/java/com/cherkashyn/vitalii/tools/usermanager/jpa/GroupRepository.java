package com.cherkashyn.vitalii.tools.usermanager.jpa;

import com.cherkashyn.vitalii.tools.usermanager.domain.Group;
import com.cherkashyn.vitalii.tools.usermanager.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {
    Optional<Group> findByName(String name);
}
