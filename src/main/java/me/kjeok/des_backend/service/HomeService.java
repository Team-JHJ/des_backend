package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.dto.HomeResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeService {
    private final HomeRepository homeRepository;
    private final HomeloadService homeloadService;
    private final InverterService inverterService;
    private final SmartmeterService smartmeterService;
    private final DerService derService;

    public List<Home> findAll() {
        return homeRepository.findAll();
    }

    public Object findById(Long homeId) {
        return homeRepository.findById(homeId);
    }

    public List<HomeResponse> getAllHomesWithFaults() {
        List<Home> homes = homeRepository.findAll(); // 모든 Home 가져오기
        return homes.stream()
                .map(home -> new HomeResponse(
                        home,
                        inverterService.getInverterIsFault(home.getId()),
                        derService.getDerIsFault(home.getId()),
                        homeloadService.getHomeloadIsFault(home.getId()),
                        smartmeterService.getSmartmeterIsFault(home.getId())
                ))
                .toList();
    }
}
