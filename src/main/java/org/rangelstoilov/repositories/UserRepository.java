package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, String> {
    User findFirstById(String id);
    User findFirstByEmail(String email);
}
