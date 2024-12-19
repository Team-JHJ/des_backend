package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Homeload;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.dto.CategoryResponse;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.HomeloadRequest;
import me.kjeok.des_backend.dto.HomeloadResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.HomeloadRepository;
import me.kjeok.des_backend.service.DescriptionService;
import me.kjeok.des_backend.service.HomeloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
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

    private Object getFieldValueByName(Object object, String fieldName) {
        try {
            // 객체의 클래스에서 필드 가져오기
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // private 필드 접근 허용
            return field.get(object); // 필드 값 반환
        } catch (NoSuchFieldException e) {
            System.err.println("Field not found: " + fieldName);
        } catch (IllegalAccessException e) {
            System.err.println("Field access error: " + fieldName);
        }
        return null; // 오류 발생 시 null 반환
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getDescriptionResponses(@RequestParam("homeId") Long homeId) {

        // Home 객체 조회
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        // Homeload 조회 및 기본 Homeload 생성
        List<Homeload> homeloadList = homeloadRepository.findByHome(home);
        if (homeloadList.isEmpty()) {
            Homeload newHomeload = Homeload.builder()
                    .home(home)
                    .type("HVAC") // 기본값
                    .operatingHours("") // 기본값
                    .powerRating(0.0f) // 기본값
                    .energyCost(0.0f) // 기본값
                    .dailyConsumption(0) // 기본값
                    .carbonFootprint(0.0f) // 기본값
                    .loadDuration(0) // 기본값
                    .powerFactor(0.0f) // 기본값
                    .loadPriority("") // 기본값
                    .smartAppliance(false) // 기본값
                    .backupPower(false) // 기본값
                    .loadFlexibility(false) // 기본값
                    .connectedDer(false) // 기본값
                    .isFault(false) // 기본값
                    .homeloadName("DefaultHomeload") // 기본값
                    .build();
            Homeload savedHomeload = homeloadRepository.save(newHomeload);
            homeloadList = List.of(savedHomeload);
        }

        // CategoryResponse 조회
        List<CategoryResponse> categoryResponses = descriptionService.getCategoryResponses("homeload_type");

        // Homeload별 DescriptionResponse 생성
        List<Map<String, Object>> homeloadResponse = homeloadList.stream()
                .map(homeload -> {
                    // Homeload별 DescriptionResponse 생성
                    List<DescriptionResponse> descriptionResponses = descriptionService.findBySource("homeload_content")
                            .stream()
                            .map(description -> {
                                Object value = null;
                                try {
                                    value = getFieldValueByName(new HomeloadResponse(homeload), description.getName());
                                } catch (RuntimeException e) {
                                    System.out.println("Error mapping Description name: " + description.getName());
                                    e.printStackTrace();
                                }
                                return new DescriptionResponse(description, value);
                            })
                            //.filter(response -> response.getValue() != null) // null 값 제거
                            .toList();

                    // Homeload 정보를 Map으로 구성
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", homeload.getId());
                    map.put("name", homeload.getHomeloadName());
                    map.put("isFault", homeload.getIsFault());
                    map.put("type", homeload.getType());
                    map.put("details", descriptionResponses); // Homeload별 고유한 DescriptionResponse 추가
                    return map;
                })
                .toList();

        // 최종 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("category", categoryResponses);
        response.put("columns", homeloadResponse);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<String> createHomeload(@RequestParam("homeId") Long homeId, @RequestParam("homeloadName") String homeloadName, @RequestParam("type") String type) {
        homeloadService.createHomeload(homeId, homeloadName, type);
        return ResponseEntity.ok(homeloadName + " created");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteHomeload(@RequestParam("homeloadId") Long homeloadId) {
        homeloadService.deleteHomeload(homeloadId);
        return ResponseEntity.ok("Homeload deleted");
    }

    @PutMapping
    public ResponseEntity<String> putHomeload(@RequestBody HomeloadRequest homeloadRequest) {
        homeloadService.putHomeload(homeloadRequest);
        return ResponseEntity.ok("Homeload updated");
    }

//    @GetMapping
//    public ResponseEntity<List<Map<String, Object>>> getAll() {
//        List<Homeload> homeloads = homeloadService.findAll();
//
//        List<Map<String, Object>> response = homeloads.stream()
//                .map(homeload -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", homeload.getId());
//                    map.put("name", homeload.getHomeloadName());
//                    map.put("isFault", homeload.getIsFault());
//                    map.put("type", homeload.getType());
//                    map.put("details", new HomeloadResponse(homeload));
//                    return map;
//                })
//                .toList();
//
//        return ResponseEntity.ok(response);
//    }
}
