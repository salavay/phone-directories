package ru.mukhamedzhanov.phonedirectory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mukhamedzhanov.phonedirectory.domain.PhoneRecord;
import ru.mukhamedzhanov.phonedirectory.domain.User;
import ru.mukhamedzhanov.phonedirectory.form.PhoneRecordForm;
import ru.mukhamedzhanov.phonedirectory.form.UserForm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserTest extends InitTest {
    final ArrayList<User> users = new ArrayList<>(List.of(
            new User("prefix1"),
            new User("2"),
            new User("prefix2")));


    @BeforeEach
    public void createUsers() {
        for (int i = 0; i < users.size(); i++) {
            final UserForm userForm = new UserForm();
            userForm.setName(users.get(i).getName());
            users.set(i, postUserForm(userForm, "/users/create"));
        }
        checkUsersSize(users.size());
        for (final User user : users) {
            final User userFromResponse = getUser(user.getId());
            Assertions.assertEquals(user.getName(), Objects.requireNonNull(userFromResponse).getName());
            //System.err.println(userFromResponse);
        }
    }

    @AfterEach
    public void deleteUsers() {
        for (final User user : users) {
            postUserForm(null, "/users/delete/" + user.getId());
        }
        checkUsersSize(0);
        checkPhonesSize(0);
    }

    @Test
    public void editUsersTest() {
        User user = users.get(0);
        final String newName = "newNameOf" + user.getName();
        final UserForm newUser = new UserForm();
        newUser.setName(newName);
        postUserForm(newUser, "/users/edit/" + user.getId());
        user = getUser(user.getId());
        Assertions.assertEquals(newName, user.getName());
    }

    @Test
    public void findByPrefixNameTest() {
        final String prefix = "prefix";
        final User[] expectedUsers = users.stream()
                .filter(user -> user.getName()
                        .startsWith(prefix)).toArray(User[]::new);
        final User[] responseUsers = restTemplate.
                postForEntity("/users/findByPrefixOfName",
                        "prefix",
                        User[].class).getBody();
        Assertions.assertNotNull(responseUsers);
        Assertions.assertTrue(Arrays.asList(responseUsers).containsAll(Arrays.asList(expectedUsers)));
    }

    @Test
    public void addPhoneTest() {
        final String number = "12345";
        final String ownerName = "OwnerOfNumber";
        final User userToAddNumber = users.get(0);
        final PhoneRecordForm phoneRecordForm = new PhoneRecordForm();
        phoneRecordForm.setNumber(number);
        phoneRecordForm.setOwnerName(ownerName);
        final PhoneRecord phoneRecord = addPhoneToUser(userToAddNumber.getId(), phoneRecordForm);
        deleteNumber(phoneRecord.getId());
        checkPhonesOfUserSize(0, userToAddNumber.getId());
    }

    @Test
    public void getAllPhonesOfUserTest() {
        final User userToAddNumber = users.get(0);
    }

    protected PhoneRecord addPhoneToUser(final long id, final PhoneRecordForm phoneRecordForm) {
        final PhoneRecord resp = restTemplate.postForEntity(
                "/users/addPhone/" + id,
                phoneRecordForm,
                PhoneRecord.class
        ).getBody();
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(resp.getNumber(), phoneRecordForm.getNumber());
        Assertions.assertEquals(resp.getOwnerName(), phoneRecordForm.getOwnerName());
        return resp;
    }

    User getUser(final long id) {
        return restTemplate.getForEntity(String.format("/users/%d", id), User.class).getBody();
    }

    User postUserForm(final UserForm form, final String url) {
        return restTemplate.postForEntity(url, form, User.class).getBody();
    }

    void checkUsersSize(final int expectedSize) {
        Assertions.assertEquals(expectedSize, restTemplate.getForObject("/users", List.class).size());
    }

    void checkPhonesOfUserSize(final int expectedSize, final long userId) {
        Assertions.assertEquals(expectedSize, restTemplate.getForObject("/users/getAllPhones/" + userId, List.class).size());
    }

    void deleteNumber(final long phoneRecordId) {
        restTemplate.getForObject("/phones/delete/" + phoneRecordId, Object.class);
    }

    void checkPhonesSize(final int expectedSize) {
        Assertions.assertEquals(expectedSize, restTemplate.getForObject("/phones", List.class).size());
    }
}
