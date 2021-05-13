package ru.mukhamedzhanov.phonedirectory.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "phone_records")
public class PhoneRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String ownerName;

    @NotEmpty
    private String number;

    @ManyToOne
    @JoinTable(name = "user_record",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private User usersHoldingRecord;
}
