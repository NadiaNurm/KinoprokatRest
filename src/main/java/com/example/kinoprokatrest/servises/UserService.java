package com.example.kinoprokatrest.servises;

import com.example.kinoprokatrest.models.Roles;
import com.example.kinoprokatrest.models.User;
import com.example.kinoprokatrest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserService {
    // Сервис связывает сущность User и репозиторий UserRepository.
    @Autowired
    UserRepository userRepository;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Long addUser(User user) {
        return userRepository.save(user).getId();
    }

    public boolean isAdmin(String username) {
        return userRepository.getUserByUsername(username).getRoles().contains(Roles.admin);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User getUserByID(Long id) {
        return userRepository.findById(id).get();
    }


}
