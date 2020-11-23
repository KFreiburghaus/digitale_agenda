package com.example.demo.controller;

import com.example.demo.dto.CalendarDto;
import com.example.demo.dto.TerminDto;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.service.TerminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/termine/terminErstellen/{id}")
public class TerminController {

    private final TerminService terminService;
    private final OwnerRepository ownerRepository;

    public TerminController(TerminService terminService, OwnerRepository ownerRepository) {
        this.terminService = terminService;
        this.ownerRepository = ownerRepository;
    }

    @PostMapping
    public ResponseEntity<String> createTermin(@RequestBody TerminDto terminDto, @PathVariable Long id) {
        terminService.createTermin(terminDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CalendarDto>> getAllTimes(@PathVariable Long id) {
        List<CalendarDto> allTimes = terminService.getAllTimes(id);
        return new ResponseEntity<>(allTimes, HttpStatus.OK);
    }

}
