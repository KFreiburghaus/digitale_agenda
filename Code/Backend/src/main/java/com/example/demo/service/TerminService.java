package com.example.demo.service;

import com.example.demo.dto.CalendarDto;
import com.example.demo.dto.TerminDto;
import com.example.demo.exceptions.VerifyException;
import com.example.demo.model.NotificationEmail;
import com.example.demo.model.Owner;
import com.example.demo.model.Termin;
import com.example.demo.model.TerminToken;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.TerminRepository;
import com.example.demo.repository.TerminTokenRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TerminService {
    private final OwnerRepository ownerRepository;
    private final TerminRepository terminRepository;
    private final MailService mailService;
    private final TerminTokenRepository terminTokenRepository;

    public TerminService(OwnerRepository ownerRepository, TerminRepository terminRepository, MailService mailService, TerminTokenRepository terminTokenRepository) {
        this.ownerRepository = ownerRepository;
        this.terminRepository = terminRepository;
        this.mailService = mailService;
        this.terminTokenRepository = terminTokenRepository;
    }

    public List<CalendarDto> getAllTimes(Long id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User nicht gefunden"));

        Set<Termin> termine = owner.getTermine();
        List<CalendarDto> calendarDtos = new ArrayList<>();

        for (Termin t : termine) {
            CalendarDto calendarDto = new CalendarDto();
            LocalDate datum = t.getDatum();
            datum.parse(t.getDatum().toString());
            calendarDto.setYear(datum.getYear());
            calendarDto.setMonth(datum.getMonth().getValue());
            calendarDto.setDay(datum.getDayOfMonth());
            calendarDto.setHoursStart(t.getHoursStart());
            calendarDto.setMinutesStart(t.getMinutesStart());
            calendarDto.setHoursEnd(t.getHoursEnd());
            calendarDto.setMinutesEnd(t.getMinutesEnd());
            calendarDto.setVorname(t.getVorname());
            calendarDto.setNachname(t.getNachname());
            calendarDto.setEmail(t.getEmail());

            calendarDtos.add(calendarDto);

        }
        return calendarDtos;
    }

    public Termin createTermin(TerminDto terminDto, Long id) {
        Termin termin = new Termin();
        termin.setVorname(terminDto.getVorname());
        termin.setNachname(terminDto.getNachname());
        termin.setEmail(terminDto.getEmail());
        termin.setDatum(terminDto.getDatum());
        termin.setHoursStart(terminDto.getHoursStart());
        termin.setMinutesStart(terminDto.getMinutesStart());
        termin.setHoursEnd(terminDto.getHoursEnd());
        termin.setMinutesEnd(terminDto.getMinutesEnd());
        Optional<Owner> owner = ownerRepository.findById(id);
        termin.setOwner(owner.orElseThrow(() -> new UsernameNotFoundException("Owner wurde nicht gefunden")));

        terminRepository.save(termin);
        String token = generateTerminToken(termin);

        mailService.sendMail(new NotificationEmail("Termin erfolgreich gebucht", terminDto.getEmail(),
                "Sie haben erfolgreich einen Termin bei " + owner.get().getFirma() + " gebucht! " +
                        "Ihr Termin ist am " + terminDto.getDatum() + " um " + String.format("%02d", termin.getHoursStart())
                        + ":" + String.format("%02d", termin.getMinutesStart()) + " bis " + String.format("%02d", termin.getHoursEnd()) + ":" + String.format("%02d", termin.getMinutesEnd()) + " Uhr. " +
                        "Falls Sie am Termin nicht teilnehmen können, wären wir Ihnen dankbar, wenn Sie ihn hier unter folgenden Link stornieren würden: " +
                        "http://localhost:8080/api/termin-loeschen/" + token
        ));
        return termin;
    }

    public String generateTerminToken(Termin termin) {
        String token = UUID.randomUUID().toString();
        TerminToken terminToken = new TerminToken();
        terminToken.setToken(token);
        terminToken.setTermin(termin);

        terminTokenRepository.save(terminToken);

        return token;
    }


    public List<CalendarDto> getAllTermins() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        }
        else {
            email = principal.toString();
        }
        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        Set<Termin> termine = owner.getTermine();
        List<CalendarDto> calendarDtos = new ArrayList<>();

        for (Termin t : termine) {
            CalendarDto calendarDto = new CalendarDto();
            LocalDate datum = t.getDatum();
            datum.parse(t.getDatum().toString());
            calendarDto.setId(t.getTerminId());
            calendarDto.setYear(datum.getYear());
            calendarDto.setMonth(datum.getMonth().getValue());
            calendarDto.setDay(datum.getDayOfMonth());
            calendarDto.setHoursStart(t.getHoursStart());
            calendarDto.setMinutesStart(t.getMinutesStart());
            calendarDto.setHoursEnd(t.getHoursEnd());
            calendarDto.setMinutesEnd(t.getMinutesEnd());
            calendarDto.setVorname(t.getVorname());
            calendarDto.setNachname(t.getNachname());
            calendarDto.setEmail(t.getEmail());

            calendarDtos.add(calendarDto);
        }
        return calendarDtos;
    }

    public void findTermin(String terminToken) {

        Optional<TerminToken> terminToken1 = terminTokenRepository.findByToken(terminToken);
        terminToken1.orElseThrow(() -> new VerifyException("Invalid Token"));
        deleteTermin(terminToken1.get());
    }

    private void deleteTermin(TerminToken terminToken) {
        Long terminId = terminToken.getTermin().getTerminId();
        terminRepository.deleteById(terminId);
    }


    public void deleteTerminFromCalendar(Long id) {
        terminRepository.deleteById(id);
    }
}
