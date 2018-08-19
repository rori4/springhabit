package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findFirstByName(String name);
}
