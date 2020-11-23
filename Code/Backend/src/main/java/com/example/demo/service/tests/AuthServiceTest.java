package com.example.demo.service.tests;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Owner;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    void signup() {
        Owner owner = new Owner();
        owner.setVorname("Tedo");
        owner.setNachname("Djukic");
        owner.setFirma("GMBH");
        owner.setEmail("t@t.com");
        owner.setPassword("1234");
        owner.setCreated(Instant.now());
        owner.setEnabled(false);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setVorname(owner.getVorname());
        registerRequest.setNachname(owner.getNachname());
        registerRequest.setEmail(owner.getEmail());
        registerRequest.setFirma(owner.getFirma());
        registerRequest.setPassword(owner.getPassword());

        String token = "token";

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setOwner(owner);


        Mockito.when(ownerRepository.save(owner)).thenReturn(owner);
        Mockito.when(authService.generateVerificationToken(owner)).thenReturn(token);
        Mockito.when(verificationTokenRepository.save(verificationToken)).thenReturn(verificationToken);

        assertEquals(owner.getFirma(), authService.signup(registerRequest).getFirma());
        assertEquals(owner.getEmail(), authService.signup(registerRequest).getEmail());
        assertEquals(owner.getNachname(), authService.signup(registerRequest).getNachname());
        assertEquals(owner.getVorname(), authService.signup(registerRequest).getVorname());
        assertEquals(owner.getPassword(), authService.signup(registerRequest).getPassword());
    }
}
