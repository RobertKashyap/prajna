package com.prajna.mentor_extension.Service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajna.mentor_extension.Entity.User;
import com.prajna.mentor_extension.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()||!user.isPresent())
            return null;
        else 
            return user.get();
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()||!user.isPresent())
            return null;
        else 
            return user.get();
    }

    public User getUserByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if(user.isEmpty()||!user.isPresent())
            return null;
        else 
            return user.get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}