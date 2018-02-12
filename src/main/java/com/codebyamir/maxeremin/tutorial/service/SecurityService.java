package com.codebyamir.maxeremin.tutorial.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String email, String password);

    boolean login(String email, String password);
}
