package com.example.demo.repository;

import com.example.demo.model.Arbeitstage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArbeitsTageRepository extends JpaRepository<Arbeitstage, Long> {
}
