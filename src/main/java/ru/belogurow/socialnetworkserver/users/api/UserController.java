package ru.belogurow.socialnetworkserver.users.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.service.UserService;

import java.util.UUID;
//import ru.belogurow.socialnetworkserver.users.dao.UserService;


/**
 * @author alexbelogurow
 */

@RestController
public class UserController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody User user) throws CustomException {
        LOGGER.info("registration({})", user);

        return ResponseEntity.ok(userService.registration(user));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user) throws CustomException {
        LOGGER.info("login({})", user);

        return ResponseEntity.ok(userService.login(user));
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity findUserById(@PathVariable(value = "id") UUID id) {
        LOGGER.info("findUserById({})", id);

        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity findAll() {
        LOGGER.info("findAll()");

        return ResponseEntity.ok(userService.findAll());
//        List<User> users = userService.findAll();
//        if (users.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(users);
    }


    @Deprecated
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity loginDeprecated(@RequestBody User user) {
        LOGGER.error("loginDeprecated({})", user);

//        switch (user.getUserStatus()) {
//            // Registration process
//            case REGISTRATION:
//                if (userService.existsByUsername(user.getUsername())) {
//                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
//                } else {
//                    Optional<User> resultUser = userService.login(user);
//                    return resultUser
//                            .map(userAccount -> ResponseEntity
//                                    .created(ServletUriComponentsBuilder
//                                            .fromCurrentRequest()
//                                            .path("/{id}")
//                                            .buildAndExpand(userAccount.getId())
//                                            .toUri())
//                                    .build())
//                            .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
//                }
//            case ONLINE:
//                // If current user is online - error
//                return ResponseEntity.notFound().build();
//            case OFFLINE:
//                // If current user is offline, check that user from db exists and not online
//                if (userService.existsByUsername(user.getUsername())) {
//
//                    Optional<User> resultUser = userService.findByUsername(user.getUsername());
//
//                    if (resultUser.isPresent()) {
//                        // check password
//                        if (!resultUser.get().getUserStatus().equals(UserStatus.ONLINE)) {
//                            if (resultUser.get().getPassword().contentEquals(user.getPassword())) {
//                                resultUser.get().setUserStatus(UserStatus.ONLINE);
//                                userService.update(resultUser.get());
//                                return ResponseEntity.ok(resultUser.get());
//                            } else {
//                                return ResponseEntity.notFound().build();
//                            }
//                        } else {
//                            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//                        }
//                    } else {
//                        return ResponseEntity.notFound().build();
//                    }
//                }
//                return ResponseEntity.notFound().build();
//                default:
//                    return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok("DEPRECATED");
    }

//    @RequestMapping(value = "/users", method = RequestMethod.POST)
//    public ResponseEntity saveUser(@RequestBody User user) {
//        LOGGER.info("saveUser({})", user);
//
//        if (userService.existsByUsername(user.getUsername())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//
//        User resultUser = userService.save(user);
//
//        return ResponseEntity
//                .created(ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(resultUser.getId())
//                        .toUri())
//                .build();
//    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable(value = "id") UUID id) {
        LOGGER.info("updateUser({}, {})", user, id);

        if (userService.existsById(user.getId())) {
            if (userService.existsByUsername(user.getUsername())) {
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

        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll() {
        LOGGER.info("deleteAll()");

        userService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
