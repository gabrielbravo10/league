package com.bravo.league.services;

import com.bravo.league.exceptions.RecordNotFoundException;
import com.bravo.league.models.User;
import com.bravo.league.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("user.notFound"));
    }

    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("user.notFound"));
    }


}
