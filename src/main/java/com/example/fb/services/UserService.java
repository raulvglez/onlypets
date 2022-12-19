package com.example.fb.services;

import com.example.fb.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getAllUsers();
    public Optional<User> getById(Long id);
    public User save(User user);
    public void deleteById(Long id);

    public Optional<User> findByUsername(String username);
}
