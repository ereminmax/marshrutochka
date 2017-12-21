package com.codebyamir.maxeremin.tutorial.service;

import com.codebyamir.maxeremin.tutorial.model.User;
import com.codebyamir.maxeremin.tutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findByResetToken(String token) {
        return userRepository.findByResetToken(token);
    }
}
