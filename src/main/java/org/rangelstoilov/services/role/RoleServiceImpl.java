package org.rangelstoilov.services.role;

import org.rangelstoilov.entities.Role;
import org.rangelstoilov.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addUserAndAdminRoleIfNotExistant(){
        if(roleRepository.findFirstByName("USER") == null){
            Role userRole = new Role("USER");
            roleRepository.save(userRole);
        }
        if(roleRepository.findFirstByName("ADMIN") == null){
            Role adminRole = new Role("ADMIN");
            roleRepository.save(adminRole);
        }
    }

    @Override
    public Role findFirstByName(String name) {
        return roleRepository.findFirstByName(name);
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }
}
