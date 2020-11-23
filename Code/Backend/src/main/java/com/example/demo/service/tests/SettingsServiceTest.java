package com.example.demo.service.tests;

import com.example.demo.model.Arbeitstage;
import com.example.demo.model.Owner;
import com.example.demo.repository.ArbeitsTageRepository;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.service.SettingsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class SettingsServiceTest {

    @Autowired
    private SettingsService settingsService;

    @MockBean
    private ArbeitsTageRepository arbeitsTageRepository;

    @MockBean
    private OwnerRepository ownerRepository;

    @Test
    void setDays() {
        Arbeitstage arbeitstage2 = new Arbeitstage();
        arbeitstage2.setMo(true);
        arbeitstage2.setDi(true);
        arbeitstage2.setMi(true);
        arbeitstage2.setDon(true);
        arbeitstage2.setFr(true);
        arbeitstage2.setSa(false);
        arbeitstage2.setSo(false);

        Owner owner2 = new Owner();
        owner2.setOwnerId(10L);
        owner2.setVorname("Tedo");
        owner2.setNachname("Djukic");
        owner2.setFirma("GMBH");
        owner2.setEnabled(true);
        owner2.setPassword("1234");
        owner2.setOwnerId(1L);
        owner2.setEmail("hehe");

        Mockito.when(ownerRepository.findById(owner2.getOwnerId())).thenReturn(Optional.of(owner2));
        Mockito.when(arbeitsTageRepository.save(arbeitstage2)).thenReturn(arbeitstage2);

        assertEquals(true, arbeitstage2.isMo());
        assertEquals(true, arbeitstage2.isDi());
        assertEquals(true, arbeitstage2.isMi());
        assertEquals(true, arbeitstage2.isDon());
        assertEquals(true, arbeitstage2.isFr());
        assertEquals(false, arbeitstage2.isSa());
        assertEquals(false, arbeitstage2.isSo());

    }

    @Test
    void updateDays() {
        Arbeitstage arbeitstage2 = new Arbeitstage();
        arbeitstage2.setMo(true);
        arbeitstage2.setDi(true);
        arbeitstage2.setMi(true);
        arbeitstage2.setDon(true);
        arbeitstage2.setFr(true);
        arbeitstage2.setSa(false);
        arbeitstage2.setSo(false);

        Owner owner2 = new Owner();
        owner2.setOwnerId(10L);
        owner2.setVorname("Tedo");
        owner2.setNachname("Djukic");
        owner2.setFirma("GMBH");
        owner2.setEnabled(true);
        owner2.setPassword("1234");
        owner2.setOwnerId(1L);
        owner2.setEmail("hehe");

        Mockito.when(ownerRepository.findById(owner2.getOwnerId())).thenReturn(Optional.of(owner2));
        Mockito.when(ownerRepository.findByEmail(owner2.getEmail())).thenReturn(Optional.of(owner2));
        Mockito.when(arbeitsTageRepository.save(arbeitstage2)).thenReturn(arbeitstage2);

        assertEquals(true, arbeitstage2.isMo());
        assertEquals(true, arbeitstage2.isDi());
        assertEquals(true, arbeitstage2.isMi());
        assertEquals(true, arbeitstage2.isDon());
        assertEquals(true, arbeitstage2.isFr());
        assertEquals(false, arbeitstage2.isSa());
        assertEquals(false, arbeitstage2.isSo());
    }

    @Test
    void getDaysByEmail() {
        Arbeitstage arbeitstage2 = new Arbeitstage();
        arbeitstage2.setMo(true);
        arbeitstage2.setDi(true);
        arbeitstage2.setMi(true);
        arbeitstage2.setDon(true);
        arbeitstage2.setFr(true);
        arbeitstage2.setSa(false);
        arbeitstage2.setSo(false);

        Owner owner2 = new Owner();
        owner2.setOwnerId(10L);
        owner2.setVorname("Tedo");
        owner2.setNachname("Djukic");
        owner2.setFirma("GMBH");
        owner2.setEnabled(true);
        owner2.setPassword("1234");
        owner2.setOwnerId(1L);
        owner2.setEmail("hehe");
        owner2.setArbeitstage(arbeitstage2);

        Mockito.when(ownerRepository.findByEmail(owner2.getEmail())).thenReturn(Optional.of(owner2));

        assertEquals(arbeitstage2.isMo(), settingsService.getDaysByEmail(owner2.getEmail()).isMonday());
        assertEquals(arbeitstage2.isDi(), settingsService.getDaysByEmail(owner2.getEmail()).isThuesday());
        assertEquals(arbeitstage2.isMi(), settingsService.getDaysByEmail(owner2.getEmail()).isWednesday());
        assertEquals(arbeitstage2.isDon(), settingsService.getDaysByEmail(owner2.getEmail()).isThursday());
        assertEquals(arbeitstage2.isFr(), settingsService.getDaysByEmail(owner2.getEmail()).isFriday());
        assertEquals(arbeitstage2.isSa(), settingsService.getDaysByEmail(owner2.getEmail()).isSaturday());
        assertEquals(arbeitstage2.isSo(), settingsService.getDaysByEmail(owner2.getEmail()).isSunday());
    }
}
