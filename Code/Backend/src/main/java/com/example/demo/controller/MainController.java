package com.example.demo.controller;

import com.example.demo.dto.OwnerDto;
import com.example.demo.service.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/allCompanies")
public class MainController {

    private final OwnerService ownerService;

    public MainController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAllCompanies() {
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.getAllOwners());
    }
}
