package ru.belogurow.socialnetworkserver.users.api;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.belogurow.socialnetworkserver.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.users.model.User;
import ru.belogurow.socialnetworkserver.users.model.UserRole;
import ru.belogurow.socialnetworkserver.users.service.impl.UserServiceImpl;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
public class UserControllerTest {

    /**
     * <a href=https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/>Mockito rest service</a>
     */

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userDatabaseService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Ignore
    @Test
    public void findUserByIdOk() throws Exception {
        User user = UserFamilyCreator.createUserWithId("getUser");


        when(userDatabaseService.findById(user.getId())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/{id}", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(user.getId().toString())))
                .andExpect(jsonPath("$.username", is("getUser")))
                .andExpect(jsonPath("$.name", is("getUser")))
                .andExpect(jsonPath("$.userRole", is(UserRole.USER.toString())));


        verify(userDatabaseService, times(1))
                .findById(user.getId());
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void getUserNotFound() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(userDatabaseService.findById(uuid)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/{id}", uuid))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());

        verify(userDatabaseService, times(1))
                .findById(uuid);
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Ignore
    @Test
    public void getAllOk() throws Exception {
        List<User> users = Arrays.asList(
                UserFamilyCreator.createUserWithId("getAll1"),
                UserFamilyCreator.createUserWithId("getAll2"));

        when(userDatabaseService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(users.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].name", is(users.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(users.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].username", is(users.get(1).getUsername())));

        verify(userDatabaseService, times(1))
                .findAll();
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Ignore
    @Test
    public void getAllNoContent() throws Exception {
        when(userDatabaseService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        verify(userDatabaseService, times(1))
                .findAll();
        verifyNoMoreInteractions(userDatabaseService);
    }

//    @Test
//    public void loginUserCreated() throws Exception {
//        User user = UserFamilyCreator.createUser("saveUser");
//
//        User userResult = UserFamilyCreator.createUserWithId(user.getUsername());
//
//        when(userDatabaseService.login(user)).thenReturn(Optional.of(userResult));
////        when(userDatabaseService.existsByUsername(user.getUsername())).thenReturn(false);
//
//        mockMvc.perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(user.toString()))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isCreated())
//                .andExpect(header().string("location", is("http://localhost/users/" + userResult.getId())));
//
//        verify(userDatabaseService, times(1))
//                .login(user);
//        verifyNoMoreInteractions(userDatabaseService);
////        verify(userAccountSe)
////        verify(userDatabaseService, times(1))
////                .existsByUsername(user.getUsername());
//    }

//    @Test
//    public void saveUserConflict() throws Exception {
//        User user = UserFamilyCreator.createUser("saveUser");
//
//        when(userDatabaseService.login(user)).thenReturn(Optional.empty());
////        when(userDatabaseService.existsByUsername(user.getUsername())).thenReturn(true);
//
//        mockMvc.perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(user.toString()))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isConflict());
//
//        verify(userDatabaseService, times(1))
//                .login(user);
//        verifyNoMoreInteractions(userDatabaseService);
//    }

    @Test
    public void updateUserConflict() throws Exception {
        User user = UserFamilyCreator.createUserWithId("updateUser");

        when(userDatabaseService.existsById(user.getId())).thenReturn(true);
        when(userDatabaseService.existsByUsername(user.getUsername())).thenReturn(true);

        mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());

        verify(userDatabaseService, times(1))
                .existsById(user.getId());
        verify(userDatabaseService, times(1))
                .existsByUsername(user.getUsername());
    }

    @Test
    public void updateUserNoContent() throws Exception {
        User user = UserFamilyCreator.createUserWithId("updateUser");

        when(userDatabaseService.existsById(user.getId())).thenReturn(true);
        when(userDatabaseService.existsByUsername(user.getUsername())).thenReturn(false);

        mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        verify(userDatabaseService, times(1))
                .existsById(user.getId());
        verify(userDatabaseService, times(1))
                .existsByUsername(user.getUsername());
    }

    @Test
    public void updateUserNotFound() throws Exception {
        User user = UserFamilyCreator.createUserWithId("updateUser");

        when(userDatabaseService.existsById(user.getId())).thenReturn(false);

        mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());

        verify(userDatabaseService, times(1))
                .existsById(user.getId());
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void deleteUserNoContent() throws Exception{
        User user = UserFamilyCreator.createUserWithId("deleteUser");

        when(userDatabaseService.findById(user.getId())).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        verify(userDatabaseService, times(1))
                .findById(user.getId());
        verify(userDatabaseService, times(1))
                .deleteById(user.getId());
    }

    @Test
    public void deleteUserNotFound() throws Exception{
        User user = UserFamilyCreator.createUserWithId("deleteUser");

        when(userDatabaseService.findById(user.getId())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());

        verify(userDatabaseService, times(1))
                .findById(user.getId());
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    @Ignore
    public void deleteAllNoContent() throws Exception {
        mockMvc.perform(delete("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        verify(userDatabaseService, times(1))
                .deleteAll();
        verifyNoMoreInteractions(userDatabaseService);
    }
}