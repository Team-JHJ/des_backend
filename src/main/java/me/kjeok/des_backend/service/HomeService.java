package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Vpp;
import me.kjeok.des_backend.dto.HomeResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.VppRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeService {
    private final HomeRepository homeRepository;
    private final VppRepository vppRepository;
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

    public HomeResponse createHome(String homeName) {
        // Vpp 객체 가져오기 (예: 기본 Vpp ID = 1L)
        Vpp defaultVpp = vppRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Default VPP not found"));

        // Home 객체 생성 및 설정
        Home home = new Home();
        home.setHomeName(homeName);
        home.setIsFault(false);
        home.setVpp(defaultVpp);

        // Home 저장
        homeRepository.save(home);

        // HomeResponse 생성 및 반환
        return new HomeResponse(
                home,
                inverterService.getInverterIsFault(home.getId()),
                derService.getDerIsFault(home.getId()),
                homeloadService.getHomeloadIsFault(home.getId()),
                smartmeterService.getSmartmeterIsFault(home.getId())
        );
    }

    @Transactional
    public void deleteHome(Long homeId, String homeName) {
        homeRepository.deleteByIdAndHomeName(homeId, homeName);
    }
}