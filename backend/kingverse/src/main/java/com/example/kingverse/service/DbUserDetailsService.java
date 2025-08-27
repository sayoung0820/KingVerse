package com.example.kingverse.service;

import com.example.kingverse.repository.UserAccountRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
@Primary
public class DbUserDetailsService implements UserDetailsService {
    private final UserAccountRepository users;

    public DbUserDetailsService(UserAccountRepository users) {
        this.users = users;
        System.out.println("==> DbUserDetailsService initialized"); // <-- should print on app start
        }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("==> DbUserDetailsService: loading user " + username);
        var ua = users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user " + username));

        System.out.println("DbUserDetailsService: loaded " + ua.getUsername()
                + " enabled=" + ua.getEnabled()
                + " hashLen=" + (ua.getPasswordHash()==null?null:ua.getPasswordHash().length())); // << TEMP

        var roles = users.findRoleNamesByUserId(ua.getUserId())
                .stream()
                .map(SimpleGrantedAuthority::new) // e.g., "ROLE_ADMIN"
                .collect(Collectors.toList());

        return User.withUsername(ua.getUsername())
                .password(ua.getPasswordHash())    // BCrypt from DB
                .authorities(roles)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!Boolean.TRUE.equals(ua.getEnabled()))  // << fix
                .build();
    }

}
