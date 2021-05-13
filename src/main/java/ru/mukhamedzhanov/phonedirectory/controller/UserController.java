package ru.mukhamedzhanov.phonedirectory.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mukhamedzhanov.phonedirectory.domain.PhoneRecord;
import ru.mukhamedzhanov.phonedirectory.domain.User;
import ru.mukhamedzhanov.phonedirectory.form.PhoneRecordForm;
import ru.mukhamedzhanov.phonedirectory.form.UserForm;
import ru.mukhamedzhanov.phonedirectory.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/create")
    public User addNewUser(@RequestBody @Valid final UserForm form,
                           final BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return userService.save(form.getName());
        }
        return null;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable final String id) {
        final Long idNumber = ControllerUtils.getLong(id);
        if (idNumber == null) return null;
        return userService.findById(idNumber);
    }

    @PostMapping("/delete/{id}")
    public void deleteUserById(@PathVariable final String id) {
        final Long idNumber = ControllerUtils.getLong(id);
        if (idNumber == null || userService.findById(idNumber) == null) return;
        userService.delete(idNumber);
    }

    @PostMapping("/edit/{id}")
    public User editUserById(@RequestBody @Valid final UserForm userForm,
                             final BindingResult bindingResult,
                             @PathVariable final String id) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        final Long idNumber = ControllerUtils.getLong(id);
        if (idNumber == null || userService.findById(idNumber) == null) return null;
        return userService.edit(idNumber, userForm.getName());
    }

    @PostMapping("/findByPrefixOfName")
    public List<User> findByPrefixOfName(@RequestBody final String prefixOfName) {
        return userService.findByPrefixOfName(prefixOfName);
    }

    @PostMapping("/addPhone/{id}")
    public PhoneRecord addNewPhoneNumber(@RequestBody @Valid final PhoneRecordForm form,
                                         final BindingResult bindingResult,
                                         @PathVariable final String id) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        final Long userId = ControllerUtils.getLong(id);
        if (userId == null || userService.findById(userId) == null) return null;
        return userService.saveRecord(userId,
                form.getOwnerName(), form.getNumber());
    }

    @GetMapping("/getAllPhones/{id}")
    public List<PhoneRecord> findAllPhonesByUserId(@PathVariable final String id) {
        final Long idNumber = ControllerUtils.getLong(id);
        if (idNumber == null) return null;
        final User user = userService.findById(idNumber);
        if (user == null) {
            return null;
        }
        return user.getNumbers();
    }
}
