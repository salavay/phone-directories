package ru.mukhamedzhanov.phonedirectory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.mukhamedzhanov.phonedirectory.domain.PhoneRecord;
import ru.mukhamedzhanov.phonedirectory.domain.User;
import ru.mukhamedzhanov.phonedirectory.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("!test")
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    private final UserService userService;

    public ApplicationRunner(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(final ApplicationArguments args) {
        final List<String> userNames = new ArrayList<>(List.of(
                "TestUser1",
                "TestUser2"));

        final ArrayList<ArrayList<String>> userPhonesOwners = new ArrayList<>();
        userPhonesOwners.add(new ArrayList<>(List.of("TestUser1NumberOne", "TestUser1NumberTwo")));
        userPhonesOwners.add(new ArrayList<>(List.of("TestUser2NumberOne")));

        final ArrayList<ArrayList<String>> userPhonesNumbers = new ArrayList<>();
        userPhonesNumbers.add(new ArrayList<>(List.of("12345", "6789")));
        userPhonesNumbers.add(new ArrayList<>(List.of("101112")));
        for (int i = 0; i < userNames.size(); i++) {
            final User user  = userService.save(userNames.get(i));
            for (int j = 0; j < userPhonesNumbers.get(i).size(); j++) {
                final PhoneRecord phoneRecord = new PhoneRecord();
                phoneRecord.setNumber(userPhonesNumbers.get(i).get(j));
                phoneRecord.setOwnerName(userPhonesOwners.get(i).get(j));
                userService.saveRecord(user.getId(),
                        phoneRecord.getOwnerName(), phoneRecord.getNumber());
            }
        }
    }
}
