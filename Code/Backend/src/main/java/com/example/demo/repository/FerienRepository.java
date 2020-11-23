package com.example.demo.repository;

import com.example.demo.model.Ferien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FerienRepository extends JpaRepository<Ferien, Long> {
}
