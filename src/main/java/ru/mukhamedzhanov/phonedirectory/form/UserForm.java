package ru.mukhamedzhanov.phonedirectory.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserForm {
    @NotEmpty
    String name;
}
