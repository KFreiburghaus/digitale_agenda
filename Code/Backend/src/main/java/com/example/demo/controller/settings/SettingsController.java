package com.example.demo.controller.settings;

import com.example.demo.dto.ArbeitstageDTO;
import com.example.demo.dto.ArbeitszeitDTO;
import com.example.demo.dto.FerienDTO;
import com.example.demo.service.SettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PostMapping("/arbeitstage/{email}")
    public ResponseEntity<ArbeitstageDTO> setDays(@RequestBody ArbeitstageDTO arbeitstageDTO, @PathVariable String email) {
        settingsService.setDays(arbeitstageDTO, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/arbeitstage/{email}")
    public ResponseEntity<ArbeitstageDTO> updateDays(@RequestBody ArbeitstageDTO arbeitstageDTO, @PathVariable String email) {
        settingsService.updateDays(arbeitstageDTO, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/arbeitstage/{email}")
    public ResponseEntity<ArbeitstageDTO> getDays(@PathVariable String email) {
        ArbeitstageDTO days = settingsService.getDaysByEmail(email);
        return new ResponseEntity<ArbeitstageDTO>(days, HttpStatus.OK);
    }


    @PostMapping("/ferien/{email}")
    public ResponseEntity<FerienDTO> setFerien(@RequestBody FerienDTO ferienDTO, @PathVariable String email) {
        settingsService.setFerien(ferienDTO, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ferien/{email}")
    public ResponseEntity<?> getAllFerien(@PathVariable String email) {
        List<FerienDTO> allTermins =
                settingsService.getAllFerien(email);
        return new ResponseEntity<>(allTermins, HttpStatus.OK);
    }

    @DeleteMapping("/ferien/{id}")
    public ResponseEntity<String> deleteFerien(@PathVariable Long id) {
        settingsService.deleteFerienById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/arbeitszeit/{email}")
    public ResponseEntity<ArbeitstageDTO> setArbeitszeit(@RequestBody ArbeitszeitDTO arbeitszeitDTO, @PathVariable String email) {
        settingsService.setArbeitszeit(arbeitszeitDTO, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/arbeitszeit/{email}")
    public ResponseEntity<ArbeitszeitDTO> getArbeitszeit(@PathVariable String email) {
        ArbeitszeitDTO zeit = settingsService.getZeitByEmail(email);
        return new ResponseEntity<ArbeitszeitDTO>(zeit, HttpStatus.OK);
    }

    @PutMapping("/arbeitszeit/{email}")
    public ResponseEntity<ArbeitszeitDTO> updateArbeitszeit(@RequestBody ArbeitszeitDTO arbeitszeitDTO, @PathVariable String email) {
        settingsService.updateZeit(arbeitszeitDTO, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
