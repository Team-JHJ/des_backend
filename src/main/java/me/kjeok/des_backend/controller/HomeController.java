package me.kjeok.des_backend.controller;

import lombok.RequiredArgsConstructor;
import me.kjeok.des_backend.domain.*;
import me.kjeok.des_backend.dto.HomeResponse;
import me.kjeok.des_backend.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {
    private final HomeService homeService;
    private final SmartmeterService smartmeterService;
    private final InverterService inverterService;
    private final HomeloadService homeloadService;
    private final DerService derService;

    @GetMapping
    public ResponseEntity<List<HomeResponse>> getAllHomes() {
        List<HomeResponse> responses = homeService.getAllHomesWithFaults();
        return ResponseEntity.ok(responses);
    }

}
