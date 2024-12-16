package me.kjeok.des_backend.service;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Vpp;
import me.kjeok.des_backend.dto.HomeResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.VppRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    public void updateHomeFaults(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        Boolean homeloadIsFault = homeloadService.getHomeloadIsFault(homeId);
        Boolean inverterIsFault = inverterService.getInverterIsFault(homeId);
        Boolean smartmeterIsFault = smartmeterService.getSmartmeterIsFault(homeId);
        Boolean derIsFault = derService.getDerIsFault(homeId);

        home.setInverterFault(inverterIsFault);
        home.setSmartmeterFault(smartmeterIsFault);
        home.setHomeloadFault(homeloadIsFault);
        home.setDerFault(derIsFault);

        homeRepository.save(home);
    }

    public List<HomeResponse> getAllHomeswithFaults() {
        // 모든 홈 조회
        List<Home> homes = homeRepository.findAll();

        // Fault 상태 업데이트 및 HomeResponse 생성
        return homes.stream()
                .map(home -> {
                    // 홈 상태 업데이트
                    updateHomeFaults(home.getId());

                    // 업데이트된 홈 다시 조회
                    Home updatedHome = homeRepository.findById(home.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Home not found"));

                    // HomeResponse 반환
                    return new HomeResponse(
                            updatedHome.getId(),
                            updatedHome.getHomeName(),
                            updatedHome.getInverterFault(),
                            updatedHome.getDerFault(),
                            updatedHome.getHomeloadFault(),
                            updatedHome.getSmartmeterFault()
                    );
                })
                .toList();
    }

    public void createHome(String homeName) {
        Home home = new Home();
        home.setHomeName(homeName);
        home.setVpp(vppRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Vpp not found")));
        home.setInverterFault(null);
        home.setHomeloadFault(null);
        home.setSmartmeterFault(null);
        home.setDerFault(null);

        homeRepository.save(home);
    }

    @Transactional
    public void deleteHome(Long homeId, String homeName) {
        homeRepository.deleteByIdAndHomeName(homeId, homeName);
    }


    public HomeResponse getHomeWithFaults(Long homeId) {
        // Home Fault 상태 업데이트
        updateHomeFaults(homeId);

        // Home 조회
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        // HomeResponse 생성 및 반환
        return new HomeResponse(
                home.getId(),
                home.getHomeName(),
                home.getInverterFault(),
                home.getDerFault(),
                home.getHomeloadFault(),
                home.getSmartmeterFault()
        );
    }

    public void updateHome(Long homeId, String homeName) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        home.setHomeName(homeName);
        homeRepository.save(home);
    }
}