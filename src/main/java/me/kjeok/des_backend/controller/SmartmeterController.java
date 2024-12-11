package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.dto.SmartmeterResponse;
import me.kjeok.des_backend.service.SmartmeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/smartmeter")
public class SmartmeterController {
    private final SmartmeterService smartmeterService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Smartmeter> smartmeters = smartmeterService.findAll();

        List<Map<String, Object>> response = smartmeters.stream()
                .map(smartmeter -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", smartmeter.getId());
                    map.put("name", smartmeter.getSmartmeterName());
                    map.put("isFault", smartmeter.getIsFault());
                    map.put("details", new SmartmeterResponse(smartmeter));
                    return map;
                })
                .toList();

        return ResponseEntity.ok(response);
    }
}
