package com.jinada.springsecurity.services;

import com.jinada.springsecurity.dtos.RegisterUserDTO;
import com.jinada.springsecurity.dtos.UserDTO;
import com.jinada.springsecurity.errors.AppError;
import com.jinada.springsecurity.models.User;
import com.jinada.springsecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            RoleService roleService
           ) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User: %s, not found", username)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getGrantedAuthorities(user)
        );
    }

    public ResponseEntity<?> createNewUser(RegisterUserDTO registerUserDTO, PasswordEncoder passwordEncoder) {
        if(findByUsername(registerUserDTO.getUsername()).isPresent()) {
            return new ResponseEntity<>(
                    new AppError(
                            HttpStatus.BAD_REQUEST.value(),
                            "user is already exist"),
                    HttpStatus.BAD_REQUEST
            );
        }

        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        userRepository.save(user);

        return ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername()));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(User user) {
        return user.getRoles().stream().map(
                (role) -> new SimpleGrantedAuthority(role.getTitle())
        ).collect(Collectors.toList());
    }
}
