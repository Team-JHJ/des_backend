package me.kjeok.des_backend.controller;

import lombok.RequiredArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/getAllHouse")
    public ResponseEntity<List<Home>> getAllHouse() {
        return ResponseEntity.ok(homeService.findAll());
    }
}
