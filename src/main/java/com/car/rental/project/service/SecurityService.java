package com.car.rental.project.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}