package com.example.demo.service;

import com.example.demo.dto.OwnerDto;
import com.example.demo.mapper.OwnerMapper;
import com.example.demo.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerService(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(ownerMapper::ownerToOwnerDto)
                .collect(Collectors.toList());
    }
}
