package ru.belogurow.socialnetworkserver.users.api.controllers;

import ru.belogurow.socialnetworkserver.users.domain.User;

import java.io.Serializable;

/**
 * @author alexbelogurow
 */
public interface UserController {
    String getUser(Serializable id);

    String insertUser(User user);
}
