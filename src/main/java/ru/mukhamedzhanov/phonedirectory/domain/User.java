package ru.mukhamedzhanov.phonedirectory.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @OneToMany
    @JoinTable(name = "user_record",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "record_id"))
    private List<PhoneRecord> numbers = new ArrayList<>();

    public User() {
    }

    public User(@NotEmpty final String name) {
        this.name = name;
    }
}
