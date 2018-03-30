package ru.belogurow.socialnetworkserver.users.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.belogurow.socialnetworkserver.SocialNetworkServerApplication;
import ru.belogurow.socialnetworkserver.users.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
@Ignore
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {
        System.out.print(userRepository.findAll());
    }

}
