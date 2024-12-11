package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Homeload;
import me.kjeok.des_backend.dto.HomeloadResponse;
import me.kjeok.des_backend.service.HomeloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/homeload")
public class HomeloadController {
    private final HomeloadService homeloadService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Homeload> homeloads = homeloadService.findAll();

        List<Map<String, Object>> response = homeloads.stream()
                .map(homeload -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", homeload.getId());
                    map.put("name", homeload.getHomeloadName());
                    map.put("isFault", homeload.getIsFault());
                    map.put("type", homeload.getType());
                    map.put("details", new HomeloadResponse(homeload));
                    return map;
                })
                .toList();

        return ResponseEntity.ok(response);
    }
}
