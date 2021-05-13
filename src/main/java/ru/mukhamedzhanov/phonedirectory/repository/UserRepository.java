package ru.mukhamedzhanov.phonedirectory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mukhamedzhanov.phonedirectory.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    List<User> findUserByName(String name);
    List<User> findUsersByNameStartingWith(String prefix);
}
