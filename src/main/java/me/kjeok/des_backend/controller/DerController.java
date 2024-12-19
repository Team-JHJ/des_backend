package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.domain.Description;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.dto.*;
import me.kjeok.des_backend.repository.DerRepository;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.service.DerService;
import me.kjeok.des_backend.service.DescriptionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/der")
public class DerController {
    private final DerService derService;
    private final DerRepository derRepository;
    private final HomeRepository homeRepository;
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

        // DER 조회 및 기본 DER 생성
        List<Der> derList = derRepository.findByHome(home);
        if (derList.isEmpty()) {
            Der newDer = Der.builder()
                    .home(home)
                    .type("") // 기본값
                    .installationDate("") // 기본값
                    .location("") // 기본값
                    .generationCapacity(0) // 기본값
                    .storageCapacity(0) // 기본값
                    .efficiency(0) // 기본값
                    .soc(0.0f) // 기본값
                    .energyGeneration(0) // 기본값
                    .gridConnection(false) // 기본값
                    .installationCosts(0) // 기본값
                    .omCosts(0) // 기본값
                    .paybackPeriod(0) // 기본값
                    .energySavings(0) // 기본값
                    .roi(0) // 기본값
                    .incentives(0) // 기본값
                    .co2Saved(0) // 기본값
                    .carbonIntensity(0.0f) // 기본값
                    .renewableShare(0) // 기본값
                    .carbonFootprint(0) // 기본값
                    .isFault(false) // 기본값
                    .battery(0) // 기본값
                    .derName("DefaultDer") // 기본값
                    .build();
            Der savedDer = derRepository.save(newDer);
            derList = List.of(savedDer);
        }

        // CategoryResponse 조회
        List<CategoryResponse> categoryResponses = descriptionService.getCategoryResponses("der_type");

        // DER별 DescriptionResponse 생성
        List<Map<String, Object>> derResponse = derList.stream()
                .map(der -> {
                    // DER별 DescriptionResponse 생성
                    List<DescriptionResponse> descriptionResponses = descriptionService.findBySource("der_content")
                            .stream()
                            .map(description -> {
                                Object value = null;
                                try {
                                    value = getFieldValueByName(new DerResponse(der), description.getName());
                                } catch (RuntimeException e) {
                                    System.out.println("Error mapping Description name: " + description.getName());
                                    e.printStackTrace();
                                }
                                return new DescriptionResponse(description, value);
                            })
                            //.filter(response -> response.getValue() != null) // null 값 제거
                            .toList();

                    // DER 정보를 Map으로 구성
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", der.getId());
                    map.put("name", der.getDerName());
                    map.put("isFault", der.getIsFault());
                    map.put("type", der.getType());
                    map.put("battery", der.getBattery());
                    map.put("details", descriptionResponses); // DER별 고유한 DescriptionResponse 추가
                    return map;
                })
                .toList();

        // 최종 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("category", categoryResponses);
        response.put("columns", derResponse);

        return ResponseEntity.ok(response);
    }



    @DeleteMapping
    public ResponseEntity<String> deleteDer(@RequestParam("derId") Long derId) {
        derRepository.deleteById(derId);
        return ResponseEntity.ok("DER deleted successfully");
    }

    @PostMapping
    public ResponseEntity<String> createDer(@RequestParam("homeId") Long homeId, @RequestParam("derName") String derName, @RequestParam("type") String type) {
        derService.createDer(homeId, derName, type);
        return ResponseEntity.ok(derName+ " created successfully");
    }

    @PutMapping
    public ResponseEntity<String> putDer(@RequestBody DerRequest derRequest) {
        derService.putDer(derRequest);
        return ResponseEntity.ok("DER updated successfully");
    }




//    @GetMapping
//    public ResponseEntity<List<Map<String, Object>>> getAll() {
//        List<Der> ders = derService.findAll();
//
//        List<Map<String, Object>> response = ders.stream()
//                .map(der -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", der.getId());
//                    map.put("name", der.getDerName());
//                    map.put("isFault", der.getIsFault());
//                    map.put("type", der.getType());
//                    map.put("battery", der.getBattery());
//                    map.put("details", new DerResponse(der));
//                    return map;
//                })
//                .toList();
//
//        return ResponseEntity.ok(response);
//    }
}
