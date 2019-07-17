package com.cherkashyn.vitalii.tools.usermanager.jpa;

import com.cherkashyn.vitalii.tools.usermanager.domain.Group;
import com.cherkashyn.vitalii.tools.usermanager.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByName(String name);

    Optional<User> findBySurname(String name);

    Optional<User> findByLogin(String name);

    Iterable<User> getByGroups(Optional<Group> savedGroup);
}
