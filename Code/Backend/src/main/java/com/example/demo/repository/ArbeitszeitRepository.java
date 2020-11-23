package com.example.demo.repository;

import com.example.demo.model.Arbeitszeit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArbeitszeitRepository extends JpaRepository<Arbeitszeit, Long> {
}
