package org.rangelstoilov.services.role;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.entities.Role;
import org.rangelstoilov.models.view.role.RoleModel;
import org.rangelstoilov.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public void addUserAndAdminRoleIfNotExist(){
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

    @Override
    public List<RoleModel> getAllRoles() {
        List<Role> all = this.roleRepository.findAll();
        java.lang.reflect.Type targetListType = new TypeToken<List<RoleModel>>() {}.getType();
        return this.modelMapper.map(all,targetListType);

    }
}
