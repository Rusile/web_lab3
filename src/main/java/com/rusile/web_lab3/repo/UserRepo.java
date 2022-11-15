package com.rusile.web_lab3.repo;

public interface UserRepo {

    boolean addUser(String login, String password);

    boolean isUserRegistered(String login, String password);
}
