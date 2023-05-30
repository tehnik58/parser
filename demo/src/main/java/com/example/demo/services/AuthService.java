package com.example.demo.services;

import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.demo.entity.User;
@Service
public class AuthService {
    private final UserRepository repository;
    @Autowired
    public AuthService(UserRepository repository)
    {
    this.repository = repository;
    }
    public User getCurrentUser()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return repository.findByUsername(currentUserName);
        }
            return null;
    }
}
