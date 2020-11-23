package com.example.demo.service.tests;

import com.example.demo.model.Owner;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @MockBean
    private OwnerRepository ownerRepository;

    @Test
    void getAllOwners() {
        Owner owner1 = new Owner();
        owner1.setVorname("Tedo");
        owner1.setNachname("Djukic");
        owner1.setEmail("t@t.com");

        Owner owner2 = new Owner();
        owner2.setVorname("Kaspar");
        owner2.setNachname("Freiburghaus");
        owner2.setEmail("k@k.com");

        Mockito.when(ownerRepository.findAll()).thenReturn(Stream.of(owner1, owner2).collect(Collectors.toList()));
        assertEquals(2, ownerService.getAllOwners().size());
    }
}
