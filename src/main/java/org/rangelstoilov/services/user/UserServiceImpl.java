package org.rangelstoilov.services.user;

import org.rangelstoilov.entities.Role;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.UserRegisterRequestModel;
import org.rangelstoilov.repositories.RoleRepository;
import org.rangelstoilov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    //TODO: separate in own service
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserRegisterRequestModel model) {
        User user = new User();
        user.setEmail(model.getEmail());
        user.setName(model.getName());
        user.setPassword(
                this.passwordEncoder.encode(model.getPassword())
        );

        Role role = this.roleRepository.findFirstByName("USER");
        role.getUsers().add(user);
        user.getRoles().add(role);

        this.roleRepository.save(role);
        return this.userRepository.save(user);
    }

    @Override
    public User findUserEntityByEmail(String email) {
        return this.userRepository.findFirstByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> roles = user.getRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName())).collect(Collectors.toSet());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
          user.getEmail(),
          user.getPassword(),
          roles
        );
        return userDetails;
    }
}
