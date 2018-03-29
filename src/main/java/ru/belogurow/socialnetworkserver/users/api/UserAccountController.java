package ru.belogurow.socialnetworkserver.users.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.belogurow.socialnetworkserver.users.model.UserAccount;
import ru.belogurow.socialnetworkserver.users.model.UserStatus;
import ru.belogurow.socialnetworkserver.users.service.UserAccountDatabaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
//import ru.belogurow.socialnetworkserver.users.dao.UserAccountDatabaseService;


/**
 * @author alexbelogurow
 */

@RestController
public class UserAccountController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserAccountController.class);

    @Autowired
    private UserAccountDatabaseService userAccountDatabaseService;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity findUserById(@PathVariable(value = "id") UUID id) {
        LOGGER.info("findUserById({})", id);

        return userAccountDatabaseService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        LOGGER.info("getAll()");

        List<UserAccount> users = userAccountDatabaseService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody UserAccount user) {
        LOGGER.info("login({})", user);

        switch (user.getUserStatus()) {
            // Registration process
            case REGISTRATION:
                if (userAccountDatabaseService.existsByUsername(user.getUsername())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                } else {
                    Optional<UserAccount> resultUser = userAccountDatabaseService.login(user);
                    return resultUser
                            .map(userAccount -> ResponseEntity
                                    .created(ServletUriComponentsBuilder
                                            .fromCurrentRequest()
                                            .path("/{id}")
                                            .buildAndExpand(userAccount.getId())
                                            .toUri())
                                    .build())
                            .orElseGet(() -> ResponseEntity.unprocessableEntity().build());
                }
            case ONLINE:
                // If current user is online - error
                return ResponseEntity.notFound().build();
            case OFFLINE:
                // If current user is offline, check that user from db exists and not online
                if (userAccountDatabaseService.existsByUsername(user.getUsername())) {

                    Optional<UserAccount> resultUser = userAccountDatabaseService.findByUsername(user.getUsername());

                    if (resultUser.isPresent()) {
                        // check password
                        if (!resultUser.get().getUserStatus().equals(UserStatus.ONLINE)) {
                            if (resultUser.get().getPassword().contentEquals(user.getPassword())) {
                                resultUser.get().setUserStatus(UserStatus.ONLINE);
                                userAccountDatabaseService.update(resultUser.get());
                                return ResponseEntity.ok(resultUser.get());
                            } else {
                                return ResponseEntity.notFound().build();
                            }
                        } else {
                            return ResponseEntity.status(HttpStatus.CONFLICT).build();
                        }
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                }
                return ResponseEntity.notFound().build();
                default:
                    return ResponseEntity.notFound().build();
        }

    }

//    @RequestMapping(value = "/users", method = RequestMethod.POST)
//    public ResponseEntity saveUser(@RequestBody UserAccount user) {
//        LOGGER.info("saveUser({})", user);
//
//        if (userAccountDatabaseService.existsByUsername(user.getUsername())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//
//        UserAccount resultUser = userAccountDatabaseService.save(user);
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
    public ResponseEntity updateUser(@RequestBody UserAccount user, @PathVariable(value = "id") UUID id) {
        LOGGER.info("updateUser({}, {})", user, id);

        if (userAccountDatabaseService.existsById(user.getId())) {
            if (userAccountDatabaseService.existsByUsername(user.getUsername())) {
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

        if (userAccountDatabaseService.findById(id).isPresent()) {
            userAccountDatabaseService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll() {
        LOGGER.info("deleteAll()");

        userAccountDatabaseService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
