package com.krizhanovsky.okitter.repository;

import com.krizhanovsky.okitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
