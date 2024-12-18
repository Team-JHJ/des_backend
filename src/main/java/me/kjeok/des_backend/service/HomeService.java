package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
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

    public void updateHomeFaultsBatch(List<Home> homes) {
        for (Home home : homes) {
            Boolean homeloadIsFault = homeloadService.getHomeloadIsFault(home.getId());
            Boolean inverterIsFault = inverterService.getInverterIsFault(home.getId());
            Boolean smartmeterIsFault = smartmeterService.getSmartmeterIsFault(home.getId());
            Boolean derIsFault = derService.getDerIsFault(home.getId());

            home.setInverterFault(inverterIsFault);
            home.setSmartmeterFault(smartmeterIsFault);
            home.setHomeloadFault(homeloadIsFault);
            home.setDerFault(derIsFault);
        }

        // 변경된 홈 데이터를 한 번에 저장
        homeRepository.saveAll(homes);
    }


    public List<HomeResponse> getAllHomeswithFaults() {
        // 모든 홈 조회
        List<Home> homes = homeRepository.findAll();

        // Fault 상태 업데이트
        updateHomeFaultsBatch(homes);

        // HomeResponse 생성
        return homes.stream()
                .map(home -> new HomeResponse(
                        home.getId(),
                        home.getHomeName(),
                        home.getInverterFault(),
                        home.getDerFault(),
                        home.getHomeloadFault(),
                        home.getSmartmeterFault()
                ))
                .toList();
    }


    public void createHome(String homeName) {
        Home home = new Home();
        home.setHomeName(homeName);
        home.setInverterFault(null);
        home.setDerFault(null);
        home.setSmartmeterFault(null);
        home.setHomeloadFault(null);

        home.setVpp(vppRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Vpp not found")));

        homeRepository.save(home);
    }

    @Transactional
    public void deleteHome(Long homeId) {
        homeRepository.deleteById(homeId);
    }

    public String getHomeNameById(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found with id: " + homeId));
        return home.getHomeName();
    }

    public String renameHome(Long homeId, String homeName) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found with id: " + homeId));
        home.setHomeName(homeName);
        homeRepository.save(home);
        return home.getHomeName();
    }


    /*public HomeResponse getHomeWithFaults(Long homeId) {
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
    }*/

    /*public void updateHome(Long homeId, String homeName) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        home.setHomeName(homeName);
        homeRepository.save(home);
    }*/
}