package ru.mukhamedzhanov.phonedirectory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mukhamedzhanov.phonedirectory.domain.PhoneRecord;

import java.util.List;

@Repository
public interface PhoneRecordRepository extends JpaRepository<PhoneRecord, Long> {

    PhoneRecord findPhoneRecordByNumberAndOwnerName(String number, String ownerName);

    List<PhoneRecord> findPhoneRecordByNumber(String number);
}
