package com.example.demo.repository;

import com.example.demo.model.TerminToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerminTokenRepository extends JpaRepository<TerminToken, Long> {
    Optional<TerminToken> findByToken(String token);
}
