package com.car.rental.project.service;

import com.car.rental.project.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}