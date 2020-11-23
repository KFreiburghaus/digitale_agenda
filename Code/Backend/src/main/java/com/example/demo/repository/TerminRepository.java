package com.example.demo.repository;

import com.example.demo.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {
}
