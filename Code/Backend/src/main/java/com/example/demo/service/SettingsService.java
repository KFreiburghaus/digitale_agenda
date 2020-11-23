package com.example.demo.service;

import com.example.demo.dto.ArbeitstageDTO;
import com.example.demo.dto.ArbeitszeitDTO;
import com.example.demo.dto.FerienDTO;
import com.example.demo.model.Arbeitstage;
import com.example.demo.model.Arbeitszeit;
import com.example.demo.model.Ferien;
import com.example.demo.model.Owner;
import com.example.demo.repository.ArbeitsTageRepository;
import com.example.demo.repository.ArbeitszeitRepository;
import com.example.demo.repository.FerienRepository;
import com.example.demo.repository.OwnerRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SettingsService {
    private final ArbeitsTageRepository arbeitsTageRepository;
    private final OwnerRepository ownerRepository;
    private final FerienRepository ferienRepository;
    private final ArbeitszeitRepository arbeitszeitRepository;

    public SettingsService(ArbeitsTageRepository arbeitsTageRepository, OwnerRepository ownerRepository, FerienRepository ferienRepository, ArbeitszeitRepository arbeitszeitRepository) {
        this.arbeitsTageRepository = arbeitsTageRepository;
        this.ownerRepository = ownerRepository;
        this.ferienRepository = ferienRepository;
        this.arbeitszeitRepository = arbeitszeitRepository;
    }

    public void setDays(ArbeitstageDTO arbeitstageDTO, String email) {
        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        Arbeitstage arbeitstage = new Arbeitstage();
        arbeitstage.setMo(arbeitstageDTO.isMonday());
        arbeitstage.setDi(arbeitstageDTO.isThuesday());
        arbeitstage.setMi(arbeitstageDTO.isWednesday());
        arbeitstage.setDon(arbeitstageDTO.isThursday());
        arbeitstage.setFr(arbeitstageDTO.isFriday());
        arbeitstage.setSa(arbeitstageDTO.isSaturday());
        arbeitstage.setSo(arbeitstageDTO.isSunday());
        arbeitstage.setOwner(owner);
        arbeitsTageRepository.save(arbeitstage);
    }


    public void updateDays(ArbeitstageDTO arbeitstageDTO, String email) {
        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        Long arbeitstageId = owner.getArbeitstage().getArbeitstageId();

        Arbeitstage arbeitstage = arbeitsTageRepository.findById(arbeitstageId).orElseThrow(() -> new UsernameNotFoundException("not found"));
        arbeitstage.setMo(arbeitstageDTO.isMonday());
        arbeitstage.setDi(arbeitstageDTO.isThuesday());
        arbeitstage.setMi(arbeitstageDTO.isWednesday());
        arbeitstage.setDon(arbeitstageDTO.isThursday());
        arbeitstage.setFr(arbeitstageDTO.isFriday());
        arbeitstage.setSa(arbeitstageDTO.isSaturday());
        arbeitstage.setSo(arbeitstageDTO.isSunday());
        arbeitstage.setOwner(owner);

        arbeitsTageRepository.save(arbeitstage);
    }

    public ArbeitstageDTO getDaysByEmail(String email) {

        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not Found"));
        Arbeitstage arbeitstage = owner.getArbeitstage();

        try {
            boolean mo = arbeitstage.isMo();
            boolean di = arbeitstage.isDi();
            boolean mi = arbeitstage.isMi();
            boolean don = arbeitstage.isDon();
            boolean fr = arbeitstage.isFr();
            boolean sa = arbeitstage.isSa();
            boolean so = arbeitstage.isSo();

            ArbeitstageDTO arbeitstageDTO = new ArbeitstageDTO(mo, di, mi, don, fr, sa, so);
            return arbeitstageDTO;
        } catch (NullPointerException e) {
            System.out.println("Arbeitstage noch nicht eingestellt!");
            boolean mo = true;
            boolean di = true;
            boolean mi = true;
            boolean don = true;
            boolean fr = true;
            boolean sa = false;
            boolean so = false;
            ArbeitstageDTO arbeitstageDTO = new ArbeitstageDTO(mo, di, mi, don, fr, sa, so);

            Arbeitstage arbeitstage2 = new Arbeitstage();
            arbeitstage2.setMo(true);
            arbeitstage2.setDi(true);
            arbeitstage2.setMi(true);
            arbeitstage2.setDon(true);
            arbeitstage2.setFr(true);
            arbeitstage2.setSa(false);
            arbeitstage2.setSo(false);
            arbeitstage2.setOwner(owner);
            arbeitsTageRepository.save(arbeitstage2);

            return arbeitstageDTO;
        }
    }

    public void setFerien(FerienDTO ferienDTO, String email) {
        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        Ferien ferien = new Ferien();
        ferien.setFerienStart(ferienDTO.getFerienStart());
        ferien.setFerienEnde(ferienDTO.getFerienEnde());
        ferien.setOwner(owner);
        ferienRepository.save(ferien);
    }

    public List<FerienDTO> getAllFerien(String email) {
        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        Set<Ferien> ferien = owner.getFerien();
        List<FerienDTO> ferienDTOS = new ArrayList<>();

        for (Ferien f : ferien) {
            FerienDTO ferienDTO = new FerienDTO();
            ferienDTO.setId(f.getFerienId());
            ferienDTO.setFerienStart(f.getFerienStart());
            ferienDTO.setFerienEnde(f.getFerienEnde());
            ferienDTOS.add(ferienDTO);
        }
        return ferienDTOS;
    }

    public void deleteFerienById(Long id) {
        Ferien ferien = ferienRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("not Found"));
        Long ferienId = ferien.getFerienId();
        ferienRepository.deleteById(ferienId);
    }

    public ArbeitszeitDTO getZeitByEmail(String email) {
        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not Found"));
        Arbeitszeit arbeitszeit = owner.getArbeitszeit();

        try {
            ArbeitszeitDTO arbeitszeitDTO = new ArbeitszeitDTO(arbeitszeit.getArbeitsbeginn(), arbeitszeit.getArbeitsende());
            return arbeitszeitDTO;
        } catch (NullPointerException e) {
            ArbeitszeitDTO arbeitszeitDTO = new ArbeitszeitDTO("06:00", "18:00");
            Arbeitszeit arbeitszeit1 = new Arbeitszeit();
            arbeitszeit1.setArbeitsbeginn("06:00");
            arbeitszeit1.setArbeitsende("18:00");
            arbeitszeit1.setOwner(owner);
            arbeitszeitRepository.save(arbeitszeit1);

            return arbeitszeitDTO;
        }

    }

    public void setArbeitszeit(ArbeitszeitDTO arbeitszeitDTO, String email) {
        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        Arbeitszeit arbeitszeit = new Arbeitszeit();
        arbeitszeit.setArbeitsbeginn(arbeitszeitDTO.getArbeitsbeginn());
        arbeitszeit.setArbeitsende(arbeitszeitDTO.getArbeitsende());

        arbeitszeit.setOwner(owner);
        arbeitszeitRepository.save(arbeitszeit);
    }


    public void updateZeit(ArbeitszeitDTO arbeitszeitDTO, String email) {
        Owner owner = ownerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        Long zeitId = owner.getArbeitszeit().getArbeitszeitId();

        Arbeitszeit arbeitszeit = arbeitszeitRepository.findById(zeitId).orElseThrow(() -> new UsernameNotFoundException("not found"));
        arbeitszeit.setArbeitsbeginn(arbeitszeitDTO.getArbeitsbeginn());

        arbeitszeit.setArbeitsende(arbeitszeitDTO.getArbeitsende());
        arbeitszeit.setOwner(owner);

        arbeitszeitRepository.save(arbeitszeit);
    }
}
