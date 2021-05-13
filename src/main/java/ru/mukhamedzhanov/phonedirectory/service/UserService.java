package ru.mukhamedzhanov.phonedirectory.service;

import org.springframework.stereotype.Service;
import ru.mukhamedzhanov.phonedirectory.domain.PhoneRecord;
import ru.mukhamedzhanov.phonedirectory.domain.User;
import ru.mukhamedzhanov.phonedirectory.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PhoneRecordService phoneRecordService;

    public UserService(final UserRepository userRepository, final PhoneRecordService phoneRecordService) {
        this.userRepository = userRepository;
        this.phoneRecordService = phoneRecordService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(final String name) {
        final User user = new User(name);
        return userRepository.save(user);
    }

    public User findById(final long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User edit(final long userId, final String newName) {
        final User user = findById(userId);
        if (user == null) {
            return null;
        }
        user.setName(newName);
        return userRepository.save(user);
    }

    public void delete(final long idNumber) {
        final User user = findById(idNumber);
        if (user == null) return;
        user.getNumbers().forEach(x -> phoneRecordService.delete(x.getId()));
        userRepository.delete(user);
    }

    public List<User> findByName(final String name) {
        return userRepository.findUserByName(name);
    }

    public List<User> findByPrefixOfName(final String prefixOfName) {
        return userRepository.findUsersByNameStartingWith(prefixOfName);
    }

    public PhoneRecord saveRecord(final Long userId, final String ownerName, final String number) {
        final User user = findById(userId);
        return phoneRecordService.saveRecord(user, ownerName, number);
    }
}
