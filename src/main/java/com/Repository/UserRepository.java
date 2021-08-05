package com.Repository;

import com.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>
{
    User findAllByUsername(String username);

    User findByActivationCode(String code);
}

