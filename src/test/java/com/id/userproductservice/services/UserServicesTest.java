package com.id.userproductservice.services;

import com.id.userproductservice.domain.ResponseStatus;
import com.id.userproductservice.domain.UserRequest;
import com.id.userproductservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles()
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class UserServicesTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;


    @Test
    public void signupTest(){
        UserRequest userRequest = UserRequest.builder()
                .username("suga")
                .email("suga@mail.com")
                .password("watermelon")
                .gender("male")
                .build();

        Mockito.when(userRepository.findOneByUsername(userRequest.getUsername())).thenReturn(null);
        Mockito.when(userRepository.findOneByEmail(userRequest.getEmail())).thenReturn(null);

        ResponseStatus response = userService.signUpUser(userRequest);
        assertNotNull(response);
    }

}
