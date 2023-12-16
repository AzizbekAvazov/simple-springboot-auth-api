package com.azizbek.fancybackservice.service;

import com.azizbek.fancybackservice.entity.Users;
import com.azizbek.fancybackservice.repository.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Azizbek Avazov on июнь, 2022
 */

@Service
public class UsersService {
    private final UsersRepo usersRepo;

    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public Users addUser(Users user) {
       return usersRepo.save(user);
    }

    public Users findById(Long user_id) {
        return usersRepo.findById(user_id).get();
    }
    public Optional<Users> findByEmail(String email) {
        return usersRepo.findByEmail(email);
    }

    public Users getUserByUsername(String username) {
        return usersRepo.getUserByUsername(username);
    }
}
