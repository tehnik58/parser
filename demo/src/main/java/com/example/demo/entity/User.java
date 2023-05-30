package com.example.demo.entity;

import jakarta.persistence.*;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity @Table(name="my_users")
public class User {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Set<Book> getFavourites() {
        return favourites;
    }
    public void addFavourite(Book book)
    {
        favourites.add(book);
    }
    @ManyToMany()
    private Set<Book> favourites;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;
    private String password;
    private boolean active;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    public User()
    {

    }



    public void setActive(boolean isActive) {
        active = true;
    }
}
