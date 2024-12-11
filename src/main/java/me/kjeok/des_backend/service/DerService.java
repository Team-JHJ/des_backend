package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.repository.DerRepository;
import me.kjeok.des_backend.repository.HomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DerService {
    private final DerRepository derRepository;
    private final HomeRepository homeRepository;

    public List<Der> findAll() {
        return derRepository.findAll();
    }

    public boolean getDerIsFault(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        return derRepository.findByHome(home).stream()
                .anyMatch(Der::getIsFault);
    }
}