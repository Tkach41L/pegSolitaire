package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Person;

public interface UserService {
    boolean login(String login, String password);

    boolean register(String login, String password);
}