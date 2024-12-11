package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.SmartmeterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SmartmeterService {
    private final SmartmeterRepository smartmeterRepository;
    private final HomeRepository homeRepository;

    public List<Smartmeter> findAll() {
        return smartmeterRepository.findAll();
    }

    public boolean getSmartmeterIsFault(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        return smartmeterRepository.findByHome(home).stream()
                .anyMatch(Smartmeter::getIsFault);
    }
}
