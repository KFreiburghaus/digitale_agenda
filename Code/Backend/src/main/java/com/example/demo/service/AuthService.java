package com.example.demo.service;

import com.example.demo.authentication.JwtProvider;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.exceptions.VerifyException;
import com.example.demo.model.NotificationEmail;
import com.example.demo.model.Owner;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.VerificationTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;
    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthService(BCryptPasswordEncoder passwordEncoder, OwnerRepository ownerRepository, MailService mailService,
                       VerificationTokenRepository verificationTokenRepository,
                       AuthenticationManager authenticationManager,
                       JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        this.passwordEncoder = passwordEncoder;

        this.ownerRepository = ownerRepository;
        this.mailService = mailService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    public RegisterRequest signup(RegisterRequest registerRequest) {
        Owner owner = new Owner();
        owner.setVorname(registerRequest.getVorname());
        owner.setNachname(registerRequest.getNachname());
        owner.setFirma(registerRequest.getFirma());
        owner.setEmail(registerRequest.getEmail());
        owner.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        owner.setCreated(Instant.now());
        owner.setEnabled(false);

        ownerRepository.save(owner);

        String token = generateVerificationToken(owner);
        mailService.sendMail(new NotificationEmail("Aktivieren Sie Ihren Account", owner.getEmail(),
                "Danke, dass Sie sich bei Digitale Agenda registriert haben, " +
                        "Bitte klicken Sie auf folgenden Link, um die Registrierung abzuschliessen : " +
                        "http://localhost:8080/api/accountVerification/" + token
        ));
        return registerRequest;
    }

    public String generateVerificationToken(Owner owner) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setOwner(owner);

        verificationTokenRepository.save(verificationToken);

        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new VerifyException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getOwner().getEmail();
        Owner owner = ownerRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with name " + username));
        owner.setEnabled(true);
        ownerRepository.save(owner);
    }

    public LoginResponse login(LoginRequest loginRequest) {
         try {
             authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(
                             loginRequest.getUsername(), loginRequest.getPassword()));
         } catch (BadCredentialsException e) {
             return new LoginResponse(null, null);
         } catch (AuthenticationException e) {
             return new LoginResponse("authenticationIsFail", null);
         }



         final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

         final String jwt = jwtProvider.generateToken(userDetails);
         String username = jwtProvider.extractUsername(jwt);

         Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         System.out.println("logged in " + principal);

         return new LoginResponse(username, jwt);
    }
}
