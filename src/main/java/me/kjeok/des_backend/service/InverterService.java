package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Inverter;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.InverterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InverterService {
    private final InverterRepository inverterRepository;
    private final HomeRepository homeRepository;

    public List<Inverter> findAll() {
        return inverterRepository.findAll();
    }

    public Boolean getInverterIsFault(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        List<Inverter> inverters = inverterRepository.findByHome(home);

        if(inverters.isEmpty())
            return null;
        else
            return inverters.stream().anyMatch(Inverter::getIsFault);
    }
}
