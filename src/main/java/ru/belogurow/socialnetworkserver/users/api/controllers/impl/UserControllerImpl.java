package ru.belogurow.socialnetworkserver.users.api.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.belogurow.socialnetworkserver.users.api.controllers.UserController;
import ru.belogurow.socialnetworkserver.users.dao.UserDatabaseService;
import ru.belogurow.socialnetworkserver.users.domain.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;

/**
 * @author alexbelogurow
 */

@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserDatabaseService userDatabaseService;

    @Override
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getUser(@RequestParam Serializable id) {
        return userDatabaseService.findById(id).toString();
    }

    @Override
    public String insertUser(@RequestBody User user) {
        throw new NotImplementedException();
    }

}
