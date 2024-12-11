package me.kjeok.des_backend.controller;

import lombok.RequiredArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.dto.HomeResponse;
import me.kjeok.des_backend.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    /*@GetMapping("/getAllHouse")
    public ResponseEntity<List<HomeResponse>> getAllHouse() {
        List<Home> homes = homeService.findAll();
        List<HomeResponse> response = homes.stream()
                .map(HomeResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }*/
}
