package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Homeload;
import me.kjeok.des_backend.dto.CategoryResponse;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.HomeloadResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.HomeloadRepository;
import me.kjeok.des_backend.service.DescriptionService;
import me.kjeok.des_backend.service.HomeloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/homeload")
public class HomeloadController {
    private final HomeloadService homeloadService;
    private final HomeRepository homeRepository;
    private final HomeloadRepository homeloadRepository;
    private final DescriptionService descriptionService;

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

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getDescriptionResponses(@RequestParam("homeId") Long homeId) {

        // Home 객체 조회
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        List<Homeload> homeloadList = homeloadRepository.findByHome(home);
        if (homeloadList.isEmpty()) {
            throw new IllegalArgumentException("Not found for the provided homeId: " + homeId);
        }

        // CategoryResponse 조회
        List<CategoryResponse> categoryResponses = descriptionService.getCategoryResponses("homeload_type");

        List<Map<String, Object>> homeloadResponse = homeloadList.stream()
                .map(homeload -> {
                    List<DescriptionResponse> descriptionResponses = homeloadService.getDescriptionResponses(home.getId(), "homeload_content");

                    Map<String, Object> map = new HashMap<>();
                    map.put("id", homeload.getId());
                    map.put("name", homeload.getHomeloadName());
                    map.put("isFault", homeload.getIsFault());
                    map.put("type", homeload.getType());
                    map.put("details", descriptionResponses); // DescriptionResponse 리스트 추가
                    return map;
                })
                .toList();

        // 최종 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("category", categoryResponses);
        response.put("columns", homeloadResponse);

        // ResponseEntity로 응답 반환
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> createHomeload(@RequestParam("homeId") Long homeId, @RequestParam("homeloadName") String homeloadName) {
        homeloadService.createHomeload(homeId, homeloadName);
        return ResponseEntity.ok("Homeload created");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteHomeload(@RequestParam("homeloadId") Long homeloadId) {
        homeloadService.deleteHomeload(homeloadId);
        return ResponseEntity.ok("Homeload deleted");
    }
}
