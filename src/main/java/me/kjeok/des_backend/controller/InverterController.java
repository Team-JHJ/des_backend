package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Inverter;
import me.kjeok.des_backend.dto.InverterResponse;
import me.kjeok.des_backend.repository.InverterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/inverter")
public class InverterController {
    private final InverterRepository inverterRepository;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Inverter> inverters = inverterRepository.findAll();

        List<Map<String, Object>> response = inverters.stream()
                .map(inverter -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", inverter.getId());
                    map.put("name", inverter.getInverterName());
                    map.put("isFault", inverter.getIsFault());
                    map.put("type", inverter.getType());
                    map.put("details", new InverterResponse(inverter));
                    return map;
                })
                .toList();

        return ResponseEntity.ok(response);
    }
}
