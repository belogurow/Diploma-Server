package ru.belogurow.socialnetworkserver.users.api.controllers;

import org.junit.Before;
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
import org.springframework.transaction.annotation.Transactional;
import ru.belogurow.socialnetworkserver.common.familyCreators.UserFamilyCreator;
import ru.belogurow.socialnetworkserver.configs.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.users.api.controllers.impl.UserControllerImpl;
import ru.belogurow.socialnetworkserver.users.dao.impl.UserDatabaseServiceImpl;
import ru.belogurow.socialnetworkserver.users.domain.User;
import ru.belogurow.socialnetworkserver.users.domain.UserStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
public class UserControllerTest {

    /**
     * <a href=https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/>Mockito rest service</a>
     */

    private MockMvc mockMvc;

    @Mock
    private UserDatabaseServiceImpl userDatabaseService;

    @InjectMocks
    private UserControllerImpl userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void getUser() throws Exception {
        User user = UserFamilyCreator.createUser("getUser");

        when(userDatabaseService.findById(user.getId())).thenReturn(user);

        mockMvc.perform(get("/users/{id}", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(user.getId().toString())))
                .andExpect(jsonPath("$.login", is("getUser")))
                .andExpect(jsonPath("$.name", is("getUser")))
                .andExpect(jsonPath("$.userStatus", is(UserStatus.USER.toString())));


        verify(userDatabaseService, times(1))
                .findById(user.getId());
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void getUserFail() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(userDatabaseService.findById(uuid)).thenReturn(null);

        mockMvc.perform(get("/users/{id}", uuid))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());

        verify(userDatabaseService, times(1))
                .findById(uuid);
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void getAll() throws Exception {
        List<User> users = Arrays.asList(
                UserFamilyCreator.createUser("getAll1"),
                UserFamilyCreator.createUser("getAll2"));

        when(userDatabaseService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(users.get(0).getId().toString())))
                .andExpect(jsonPath("$[0].login", is(users.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(users.get(1).getId().toString())))
                .andExpect(jsonPath("$[1].login", is(users.get(1).getName())));

        verify(userDatabaseService, times(1))
                .findAll();
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void insertUser() throws Exception {
        User user = UserFamilyCreator.createUser("insertUser");

        when(userDatabaseService.insert(user)).thenReturn(true);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", is("http://localhost/users/" + user.getId())));

        verify(userDatabaseService, times(1))
                .insert(user);
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void insertUserFail() throws Exception {
        User user = UserFamilyCreator.createUser("insertUser");

        when(userDatabaseService.insert(user)).thenReturn(false);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict())
                .andExpect(header().doesNotExist("location"));

        verify(userDatabaseService, times(1))
                .insert(user);
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void updateUser() throws Exception {
        User user = UserFamilyCreator.createUser("updateUser");

        when(userDatabaseService.update(user)).thenReturn(true);

        mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(userDatabaseService, times(1))
                .update(user);
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void updateUserFail() throws Exception {
        User user = UserFamilyCreator.createUser("updateUserFail");

        when(userDatabaseService.update(user)).thenReturn(false);

        mockMvc.perform(put("/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());

        verify(userDatabaseService, times(1))
                .update(user);
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void deleteUser() throws Exception{
        User user = UserFamilyCreator.createUser("deleteUser");

        when(userDatabaseService.findById(user.getId())).thenReturn(user);
        when(userDatabaseService.delete(user)).thenReturn(true);

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(userDatabaseService, times(1))
                .delete(user);
        verify(userDatabaseService, times(1))
                .findById(user.getId());
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void deleteUserFail1() throws Exception {
        User user = UserFamilyCreator.createUser("deleteUserFail1");

        when(userDatabaseService.findById(user.getId())).thenReturn(null);

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());

        verify(userDatabaseService, times(1))
                .findById(user.getId());
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void deleteUserFail2() throws Exception {
        User user = UserFamilyCreator.createUser("deleteUserFail2");

        when(userDatabaseService.findById(user.getId())).thenReturn(user);
        when(userDatabaseService.delete(user)).thenReturn(false);

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());

        verify(userDatabaseService, times(1))
                .delete(user);
        verify(userDatabaseService, times(1))
                .findById(user.getId());
    }

    @Test
    public void deleteAll() throws Exception {
        when(userDatabaseService.deleteAll()).thenReturn(true);

        mockMvc.perform(delete("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(userDatabaseService, times(1))
                .deleteAll();
        verifyNoMoreInteractions(userDatabaseService);
    }

    @Test
    public void deleteAllFail() throws Exception {
        when(userDatabaseService.deleteAll()).thenReturn(false);

        mockMvc.perform(delete("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        verify(userDatabaseService, times(1))
                .deleteAll();
        verifyNoMoreInteractions(userDatabaseService);
    }

}
