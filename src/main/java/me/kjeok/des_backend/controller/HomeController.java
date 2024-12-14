package me.kjeok.des_backend.controller;

import lombok.RequiredArgsConstructor;
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
        List<HomeResponse> responses = homeService.getAllHomesWithFaults();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<HomeResponse> createHome(String homeName) {
        HomeResponse response = homeService.createHome(homeName);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteHome(Long homeId, String homeName  ) {
        homeService.deleteHome(homeId, homeName);
        return ResponseEntity.ok("Home " + homeName + " deleted");
    }

}
