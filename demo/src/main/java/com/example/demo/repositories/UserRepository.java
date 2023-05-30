package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.entity.User;


    public interface UserRepository extends CrudRepository<User, Long>
    {
        User findByUsername(String username);
    }


