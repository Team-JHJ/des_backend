package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Vpp;
import me.kjeok.des_backend.dto.VppResponse;
import me.kjeok.des_backend.repository.VppRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vpp")
public class VppController {
    private final VppRepository vppRepository;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Vpp> vpps = vppRepository.findAll();

        List<Map<String, Object>> response = vpps.stream()
                .map(vpp -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", vpp.getId());
                    map.put("isFault", vpp.getIsFault());
                    map.put("details", new VppResponse(vpp));
                    return map;
                })
                .toList();
        return ResponseEntity.ok(response);
    }
}
