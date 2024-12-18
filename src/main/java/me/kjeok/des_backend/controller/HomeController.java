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

    @GetMapping
    public ResponseEntity<List<HomeResponse>> getAllHomes() {
        List<HomeResponse> homes = homeService.getAllHomeswithFaults();
        return ResponseEntity.ok(homes);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteHome(@RequestParam Long homeId) {
        homeService.deleteHome(homeId);
        return ResponseEntity.ok("Home Deleted");
    }

    @PostMapping
    public ResponseEntity<String> createHome(@RequestParam String homeName) {
        homeService.createHome(homeName);
        return ResponseEntity.ok(homeName + " created");
    }

    @GetMapping("/{homeId}")
    public ResponseEntity<String> getHomeNameById(@PathVariable Long homeId) {
        String homeName = homeService.getHomeNameById(homeId);
        return ResponseEntity.ok(homeName);
    }

    @PutMapping("/{homeId}")
    public ResponseEntity<String> updateHomeName(@PathVariable Long homeId, @RequestParam String homeName) {
        homeService.renameHome(homeId, homeName);
        return ResponseEntity.ok("Home " + homeId + " updated");
    }

//    @PutMapping("/{homeId}")
//    public ResponseEntity<String> updateHomeName(@PathVariable Long homeId, String homeName) {
//        homeService.updateHome(homeId, homeName);
//        return ResponseEntity.ok("Home " + homeId + " updated");
//    }
}
