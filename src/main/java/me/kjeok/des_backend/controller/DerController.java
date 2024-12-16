package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.domain.Description;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.dto.DerResponse;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.repository.DerRepository;
import me.kjeok.des_backend.service.DerService;
import me.kjeok.des_backend.service.DescriptionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/der")
public class DerController {
    private final DerService derService;
    private final DerRepository derRepository;

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

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> getDescriptionResponses(@RequestParam("homeId") Long homeId, @RequestParam("type") String type, @RequestParam("derName") String derName) {

        // Home 객체 생성
        Home home = new Home();
        home.setId(homeId);

        // Der 조회
        List<Der> derList = derRepository.findByHomeAndTypeAndDerName(home, type, derName);
        if (derList.isEmpty()) {
            throw new IllegalArgumentException("No DERs found for the provided criteria.");
        }

        // DescriptionResponse 리스트 생성
        List<DescriptionResponse> descriptionResponses = derService.getDescriptionResponses(homeId, type, derName);

        // Der + DescriptionResponse 데이터를 결합하여 Map 생성
        List<Map<String, Object>> response = derList.stream()
                .map(der -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", der.getId());
                    map.put("name", der.getDerName());
                    map.put("isFault", der.getIsFault());
                    map.put("type", der.getType());
                    map.put("battery", der.getBattery());
                    map.put("details", descriptionResponses); // DescriptionResponse 리스트 추가
                    return map;
                })
                .toList();

        // ResponseEntity로 응답
        return ResponseEntity.ok(response);
    }
}
