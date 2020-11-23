package com.example.demo.service.tests;

import com.example.demo.dto.TerminDto;
import com.example.demo.model.Owner;
import com.example.demo.model.Termin;
import com.example.demo.model.TerminToken;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.TerminRepository;
import com.example.demo.repository.TerminTokenRepository;
import com.example.demo.service.TerminService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class TerminServiceTest {

    @Autowired
    private TerminService terminService;

    @MockBean
    private TerminRepository terminRepository;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private TerminTokenRepository terminTokenRepository;

    @Test
    void createTermin() {
        Owner owner2 = new Owner();
        owner2.setOwnerId(10L);
        owner2.setVorname("Tedo");
        owner2.setNachname("Djukic");
        owner2.setFirma("GMBH");
        owner2.setEnabled(true);
        owner2.setPassword("1234");
        owner2.setOwnerId(1L);
        owner2.setEmail("hehe");

        Termin termin = new Termin();
        termin.setVorname("Konj");
        termin.setNachname("Konjo");
        termin.setEmail("k@k.com");
        termin.setDatum(LocalDate.now());
        termin.setHoursStart(19);
        termin.setMinutesStart(0);
        termin.setHoursEnd(20);
        termin.setMinutesEnd(0);

        TerminDto terminDto = new TerminDto();
        terminDto.setVorname("Konj");
        terminDto.setNachname("Konjo");
        terminDto.setEmail("k@k.com");
        terminDto.setDatum(LocalDate.now());
        terminDto.setHoursStart(19);
        terminDto.setMinutesStart(0);
        terminDto.setHoursEnd(20);
        terminDto.setMinutesEnd(0);
        String token = "token";

        TerminToken terminToken = new TerminToken();
        terminToken.setToken(token);
        terminToken.setTermin(termin);

        Mockito.when(ownerRepository.findById(owner2.getOwnerId())).thenReturn(Optional.of(owner2));
        Mockito.when(terminRepository.save(termin)).thenReturn(termin);
        Mockito.when(terminService.generateTerminToken(termin)).thenReturn(token);
        Mockito.when(terminTokenRepository.save(terminToken)).thenReturn(terminToken);

        assertEquals(termin.getVorname(), terminService.createTermin(terminDto, owner2.getOwnerId()).getVorname());
        assertEquals(termin.getDatum(), terminService.createTermin(terminDto, owner2.getOwnerId()).getDatum());
        assertEquals(termin.getHoursStart(), terminService.createTermin(terminDto, owner2.getOwnerId()).getHoursStart());
        assertEquals(termin.getHoursEnd(), terminService.createTermin(terminDto, owner2.getOwnerId()).getHoursEnd());
        assertEquals(termin.getMinutesStart(), terminService.createTermin(terminDto, owner2.getOwnerId()).getMinutesStart());
        assertEquals(termin.getMinutesEnd(), terminService.createTermin(terminDto, owner2.getOwnerId()).getMinutesEnd());

    }
}
