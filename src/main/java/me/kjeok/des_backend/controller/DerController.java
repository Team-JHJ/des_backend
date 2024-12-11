package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.dto.DerResponse;
import me.kjeok.des_backend.service.DerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class DerController {
    private final DerService derService;

    @GetMapping("/api/getAllDers")
    public ResponseEntity<List<Map<String, Object>>> getAllDers() {
        List<Der> ders = derService.findAll();

        List<Map<String, Object>> response = ders.stream()
                .map(der -> Map.of(
                        "item", Map.of(
                                "battery", der.getBattery(),
                                "isFault", der.getIsFault(),
                                "derName", der.getDerName(),
                                "type", der.getType(),
                                "id", der.getId()
                        ),
                        "details", new DerResponse(der)
                ))
                .toList();
        return ResponseEntity.ok(response);
    }
}
