package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.dto.DerResponse;
import me.kjeok.des_backend.service.DerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
@RequestMapping("/api/der")
public class DerController {
    private final DerService derService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Der> ders = derService.findAll();

        List<Map<String, Object>> response = ders.stream()
                .map(der -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", der.getId());
                    map.put("name", der.getDerName());
                    map.put("isFault", der.getIsFault());
                    map.put("type", der.getType());
                    map.put("battery", der.getBattery());
                    map.put("details", new DerResponse(der));
                    return map;
                })
                .toList();

        return ResponseEntity.ok(response);
    }
}
