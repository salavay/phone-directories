package ru.mukhamedzhanov.phonedirectory.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PhoneRecordForm {
    @NotEmpty
    String ownerName;

    @NotEmpty
    String number;
}
