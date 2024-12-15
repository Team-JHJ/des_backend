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

    public Boolean getSmartmeterIsFault(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        List<Smartmeter> smartmeters = smartmeterRepository.findByHome(home);

        if(smartmeters.isEmpty())
            return null;
        else
            return smartmeters.stream().anyMatch(Smartmeter::getIsFault);
    }
}
