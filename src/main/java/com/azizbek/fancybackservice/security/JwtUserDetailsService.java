package com.azizbek.fancybackservice.security;

import com.azizbek.fancybackservice.entity.Users;
import com.azizbek.fancybackservice.service.UsersService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Creator: Azizbek Avazov
 * Date: 01.07.2022
 * Time: 10:20
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UsersService usersService;

    public JwtUserDetailsService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> user = usersService.findByEmail(email);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User with email:" + email + " not found");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
    }
}
