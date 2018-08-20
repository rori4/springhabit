package org.rangelstoilov.services.role;

import org.rangelstoilov.entities.Role;

public interface RoleService {
    void addUserAndAdminRoleIfNotExistant();
    Role findFirstByName(String name);
    void addRole(Role role);
}
