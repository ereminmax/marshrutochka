package com.codebyamir.maxeremin.tutorial.repository;

import com.codebyamir.maxeremin.tutorial.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByConfirmationToken(String confirmationToken);
    User findByResetToken(String token);
}
