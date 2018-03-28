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
import ru.belogurow.socialnetworkserver.users.repository.UserAccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SocialNetworkServerApplication.class})
@ActiveProfiles(profiles = "test")
@Transactional
@Ignore
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void test() {
        System.out.print(userAccountRepository.findAll());
    }

}
