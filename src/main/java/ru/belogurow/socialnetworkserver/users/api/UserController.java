package ru.belogurow.socialnetworkserver.users.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.service.UserDatabaseService;

import java.util.List;
import java.util.UUID;
//import ru.belogurow.socialnetworkserver.users.dao.UserDatabaseService;


/**
 * @author alexbelogurow
 */

@RestController
public class UserController{

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDatabaseService userDatabaseService;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity findUserById(@PathVariable(value = "id") UUID id) {
        LOGGER.info("findUserById({})", id);

        return userDatabaseService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        LOGGER.info("getAll()");

        List<User> users = userDatabaseService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody User user) {
        LOGGER.info("saveUser({})", user);

        if (userDatabaseService.existsByLogin(user.getLogin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User resultUser = userDatabaseService.save(user);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(resultUser.getId())
                        .toUri())
                .build();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable(value = "id") UUID id) {
        LOGGER.info("updateUser({}, {})", user, id);

        if (userDatabaseService.existsById(user.getId())) {
            if (userDatabaseService.existsByLogin(user.getLogin())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable(value = "id") UUID id) {
        LOGGER.info("deleteUser({})", id);

        if (userDatabaseService.findById(id).isPresent()) {
            userDatabaseService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll() {
        LOGGER.info("deleteAll()");

        userDatabaseService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
