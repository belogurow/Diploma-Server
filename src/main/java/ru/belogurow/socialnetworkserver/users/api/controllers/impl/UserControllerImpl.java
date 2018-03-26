package ru.belogurow.socialnetworkserver.users.api.controllers.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.belogurow.socialnetworkserver.users.api.controllers.UserController;
import ru.belogurow.socialnetworkserver.users.dao.UserDatabaseService;
import ru.belogurow.socialnetworkserver.users.domain.User;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author alexbelogurow
 */

@RestController
public class UserControllerImpl implements UserController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    private UserDatabaseService userDatabaseService;

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable(value = "id") UUID id) {
        LOGGER.info("getUser({})", id);

        Optional<User> user = Optional.ofNullable(userDatabaseService.findById(id));

        return user
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAll() {
        LOGGER.info("getAll()");

        List<User> users = userDatabaseService.findAll();
        return users;
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity insertUser(@RequestBody User user) {
        LOGGER.info("insertUser({})", user);

        if (userDatabaseService.insert(user)) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable(value = "id") UUID id) {
        LOGGER.info("updateUser({}, {})", user, id);

        if (userDatabaseService.update(user)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable(value = "id") UUID id) {
        LOGGER.info("deleteUser({})", id);

        Optional<User> user = Optional.ofNullable(userDatabaseService.findById(id));

        if (user.isPresent() && userDatabaseService.delete(user.get())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll() {
        LOGGER.info("deleteAll()");

        if (userDatabaseService.deleteAll()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }




}
