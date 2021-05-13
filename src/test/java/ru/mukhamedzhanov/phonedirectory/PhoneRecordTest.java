package ru.mukhamedzhanov.phonedirectory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mukhamedzhanov.phonedirectory.domain.PhoneRecord;
import ru.mukhamedzhanov.phonedirectory.domain.User;
import ru.mukhamedzhanov.phonedirectory.form.PhoneRecordForm;

import java.util.Arrays;

public class PhoneRecordTest extends UserTest {

    @Test
    public void addPhoneToUserTest() {
        addPhoneTest();
    }

    @Test
    public void editPhoneTest() {
        final User user = users.get(0);
        final PhoneRecordForm phoneRecordForm = new PhoneRecordForm();
        final String oldOwnerName = "oldName";
        final String oldNumber = "oldNumber";
        final String newOwnerName = "newName";
        final String newNumber = "newNumber";
        phoneRecordForm.setOwnerName(oldOwnerName);
        phoneRecordForm.setNumber(oldNumber);
        PhoneRecord phoneRecord = addPhoneToUser(user.getId(), phoneRecordForm);
        phoneRecordForm.setNumber(newNumber);
        phoneRecordForm.setOwnerName(newOwnerName);

        phoneRecord = restTemplate.postForEntity("/phones/edit/" + phoneRecord.getId(),
                phoneRecordForm,
                PhoneRecord.class).getBody();

        Assertions.assertNotNull(phoneRecord);
        Assertions.assertEquals(phoneRecord.getOwnerName(), newOwnerName);
        Assertions.assertEquals(phoneRecord.getNumber(), newNumber);

        deleteNumber(phoneRecord.getId());
        checkPhonesOfUserSize(0, user.getId());
    }

    @Test
    public void findByNumberTest() {
        final User user = users.get(0);
        final PhoneRecordForm phoneRecordForm = new PhoneRecordForm();
        final String number = "88005553535";
        final String ownerName = "ownerName";
        phoneRecordForm.setOwnerName(ownerName);
        phoneRecordForm.setNumber(number);
        addPhoneToUser(user.getId(), phoneRecordForm);

        final PhoneRecord[] phoneRecords = restTemplate.postForEntity(
                "/phones/findByNumber",
                phoneRecordForm.getNumber(),
                PhoneRecord[].class
        ).getBody();

        Assertions.assertNotNull(phoneRecords);
        Assertions.assertTrue(Arrays.stream(phoneRecords)
                .anyMatch(phoneRecord ->
                        phoneRecord.getNumber().equals(number) &&
                                phoneRecord.getOwnerName().equals(ownerName)));
    }
}
