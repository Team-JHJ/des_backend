package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Homeload;
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

    public Boolean getHomeloadIsFault(Long homeId) {
        // Home 조회
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        // Homeload가 존재하는지 확인
        List<Homeload> homeloads = homeloadRepository.findByHome(home);
        if (homeloads.isEmpty())
            return null;
        else
            return homeloads.stream().anyMatch(Homeload::getIsFault);
    }
}