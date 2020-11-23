package com.example.demo.controller;

import com.example.demo.service.TerminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/termin-loeschen/{token}")
public class StornController {

    private final TerminService terminService;

    public StornController(TerminService terminService) {
        this.terminService = terminService;
    }

    @GetMapping
    public ResponseEntity<String> deleteTermin(@PathVariable String token) {
        terminService.findTermin(token);
        return new ResponseEntity<>("Termin erfolgreich storniert", HttpStatus.OK);
    }
}
