package com.example.demo.services;

import com.example.demo.entity.Book;
import com.example.demo.entity.Role;
import com.example.demo.repositories.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserService implements UserDetailsService {
private final UserRepository repository;
@Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
        addAdmin();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = repository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(myUser.getUsername(),
                myUser.getPassword(), mapRolesToAthorities(myUser.getRoles()));

    }
    private List<? extends GrantedAuthority> mapRolesToAthorities(Set<Role> roles)
    {
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" +r.name())).collect(Collectors.toList());
    }
    public void addUser(User user) throws Exception {
        User userFromDb = repository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            throw new Exception("user exist");
        }
        user.setRoles(Collections.singleton(Role.User));
        user.setActive(true);
        repository.save(user);

    }
    private void addAdmin()
    {
        User adminFromDb = repository.findByUsername("admin");
        if (adminFromDb!=null){
            return;
        }
        User admin = new User();
        admin.setRoles(Collections.singleton(Role.Admin));
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setActive(true);
        repository.save(admin);

    }
    public void addToFavourites(Book book, User user)
    {
        user.addFavourite(book);
        repository.save(user);
    }


    }
