package ru.mukhamedzhanov.phonedirectory.service;

import org.springframework.stereotype.Service;
import ru.mukhamedzhanov.phonedirectory.domain.PhoneRecord;
import ru.mukhamedzhanov.phonedirectory.domain.User;
import ru.mukhamedzhanov.phonedirectory.repository.PhoneRecordRepository;

import java.util.List;

@Service
public class PhoneRecordService {

    private final PhoneRecordRepository phoneRecordRepository;

    public PhoneRecordService(final PhoneRecordRepository phoneRecordRepository) {
        this.phoneRecordRepository = phoneRecordRepository;
    }

    public PhoneRecord saveRecord(final User user,
                                  final String ownerName,
                                  final String number) {
        final PhoneRecord phoneRecord = new PhoneRecord();
        phoneRecord.setOwnerName(ownerName);
        phoneRecord.setNumber(number);
        phoneRecord.setUsersHoldingRecord(user);
        return phoneRecordRepository.save(phoneRecord);
    }

    public List<PhoneRecord> findAll() {
        return phoneRecordRepository.findAll();
    }

    public List<PhoneRecord> findByPhone(final String number) {
        return phoneRecordRepository.findPhoneRecordByNumber(number);
    }

    public void delete(final Long idNumber) {
        phoneRecordRepository.deleteById(idNumber);
    }

    public PhoneRecord findById(final Long idNumber) {
        return phoneRecordRepository.findById(idNumber).orElse(null);
    }

    public PhoneRecord edit(final long id, final String ownerName, final String number) {
        final PhoneRecord phoneRecord = findById(id);
        phoneRecord.setOwnerName(ownerName);
        phoneRecord.setNumber(number);
        return phoneRecordRepository.save(phoneRecord);
    }
}
