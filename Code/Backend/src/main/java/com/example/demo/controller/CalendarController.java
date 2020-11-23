
package com.example.demo.controller;

import com.example.demo.dto.CalendarDto;
import com.example.demo.service.TerminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/calendar")
public class CalendarController {

    private final TerminService terminService;

    public CalendarController(TerminService terminService) {
        this.terminService = terminService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTermins() {
        List<CalendarDto> allTermins =
                terminService.getAllTermins();
        return new ResponseEntity<>(allTermins, HttpStatus.OK);
    }


    @DeleteMapping("/{idS}")
    public ResponseEntity deleteTermin(@PathVariable String idS) {
        Long id = Long.parseLong(idS);
        terminService.deleteTerminFromCalendar(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

