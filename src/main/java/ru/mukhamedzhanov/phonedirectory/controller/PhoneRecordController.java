package ru.mukhamedzhanov.phonedirectory.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mukhamedzhanov.phonedirectory.domain.PhoneRecord;
import ru.mukhamedzhanov.phonedirectory.form.PhoneRecordForm;
import ru.mukhamedzhanov.phonedirectory.service.PhoneRecordService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/phones")
public class PhoneRecordController {
    private final PhoneRecordService phoneRecordService;

    public PhoneRecordController(final PhoneRecordService phoneRecordService) {
        this.phoneRecordService = phoneRecordService;
    }

    @GetMapping("")
    public List<PhoneRecord> findAll() {
        return phoneRecordService.findAll();
    }

    @GetMapping("/{id}")
    public PhoneRecord getNumberById(@PathVariable final String id) {
        final Long idNumber = ControllerUtils.getLong(id);
        if (idNumber == null) return null;
        return phoneRecordService.findById(idNumber);
    }

    @GetMapping("/delete/{id}")
    public void deletePhoneRecord(@PathVariable final String id) {
        final Long idNumber = ControllerUtils.getLong(id);
        if (idNumber == null || phoneRecordService.findById(idNumber) == null) return;
        phoneRecordService.delete(idNumber);
    }

    @PostMapping("/edit/{id}")
    public PhoneRecord editPhoneRecord(@RequestBody @Valid final PhoneRecordForm phoneRecordForm,
                                final BindingResult bindingResult,
                                @PathVariable final String id) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        final Long idNumber = ControllerUtils.getLong(id);
        if (idNumber == null || phoneRecordService.findById(idNumber) == null) return null;
        return phoneRecordService.edit(idNumber, phoneRecordForm.getOwnerName(), phoneRecordForm.getNumber());
    }


    @PostMapping("/findByNumber")
    public List<PhoneRecord> findByNumber(@RequestBody final String number) {
        return phoneRecordService.findByPhone(number);
    }
}