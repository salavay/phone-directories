package ru.mukhamedzhanov.phonedirectory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ru.mukhamedzhanov.phonedirectory.service.PhoneRecordService;
import ru.mukhamedzhanov.phonedirectory.service.UserService;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class InitTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserService userService;

    @Autowired
    PhoneRecordService phoneRecordService;
}