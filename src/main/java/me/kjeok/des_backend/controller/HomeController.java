package me.kjeok.des_backend.controller;

import lombok.RequiredArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.dto.HomeResponse;
import me.kjeok.des_backend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {
    private final HomeService homeService;



//    @GetMapping("/{homeId}/faults")
//    public ResponseEntity<HomeResponse> getHomeFaults(@PathVariable Long homeId) {
//        HomeResponse response = homeService.getHomeWithFaults(homeId);
//        return ResponseEntity.ok(response);
//    }


    @GetMapping
    public ResponseEntity<List<HomeResponse>> getAllHomes() {
        List<HomeResponse> homes = homeService.getAllHomeswithFaults();
        return ResponseEntity.ok(homes);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteHome(Long homeId, String homeName  ) {
        homeService.deleteHome(homeId, homeName);
        return ResponseEntity.ok("Home " + homeName + " deleted");
    }

    @PostMapping
    public ResponseEntity<String> createHome(String homeName) {
        homeService.createHome(homeName);
        return ResponseEntity.ok("Home " + homeName + " created");
    }

    @PutMapping("/{homeId}")
    public ResponseEntity<String> updateHomeName(@PathVariable Long homeId, String homeName) {
        homeService.updateHome(homeId, homeName);
        return ResponseEntity.ok("Home " + homeId + " updated");
    }

}
