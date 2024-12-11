package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Homeload;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.HomeloadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeloadService {
    private final HomeloadRepository homeloadRepository;
    private final HomeRepository homeRepository;

    public List<Homeload> findAll() {
        return homeloadRepository.findAll();
    }

    public boolean getHomeloadIsFault(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        return homeloadRepository.findByHome(home).stream()
                .anyMatch(Homeload::getIsFault);
    }
}