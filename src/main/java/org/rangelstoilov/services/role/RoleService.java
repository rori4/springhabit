package org.rangelstoilov.services.role;

import org.rangelstoilov.entities.Role;
import org.rangelstoilov.models.role.RoleModel;

import java.util.List;

public interface RoleService {
    void addUserAndAdminRoleIfNotExist();
    Role findFirstByName(String name);
    void addRole(Role role);

    List<RoleModel> getAllRoles();
}
