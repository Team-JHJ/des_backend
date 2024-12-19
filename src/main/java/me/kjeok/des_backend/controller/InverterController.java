package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Inverter;
import me.kjeok.des_backend.dto.CategoryResponse;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.InverterRequest;
import me.kjeok.des_backend.dto.InverterResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.InverterRepository;
import me.kjeok.des_backend.service.DescriptionService;
import me.kjeok.des_backend.service.InverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/inverter")
public class InverterController {
    private final InverterRepository inverterRepository;
    private final HomeRepository homeRepository;
    private final DescriptionService descriptionService;
    private final InverterService inverterService;


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

        // Inverter 조회 및 기본 Inverter 생성
        List<Inverter> inverterList = inverterRepository.findByHome(home);
        if (inverterList.isEmpty()) {
            Inverter newInverter = Inverter.builder()
                    .home(home)
                    .type("String")
                    .installationDate("") // 기본값
                    .efficiency(0) // 기본값
                    .warranty(0) // 기본값
                    .capacityFactor(0) // 기본값
                    .mpptCount(0) // 기본값
                    .manufacturer("") // 기본값
                    .model("") // 기본값
                    .phaseType("") // 기본값
                    .status(false) // 기본값
                    .monitoring(false) // 기본값
                    .gridTie(false) // 기본값
                    .isFault(false) // 기본값
                    .inverterName("DefaultInverter") // 기본값
                    .build();
            Inverter savedInverter = inverterRepository.save(newInverter);
            inverterList = List.of(savedInverter);
        }

        // CategoryResponse 조회
        List<CategoryResponse> categoryResponses = descriptionService.getCategoryResponses("inverter_type");

        // Inverter별 DescriptionResponse 생성
        List<Map<String, Object>> inverterResponse = inverterList.stream()
                .map(inverter -> {
                    // Inverter별 DescriptionResponse 생성
                    List<DescriptionResponse> descriptionResponses = descriptionService.findBySource("inverter_content")
                            .stream()
                            .map(description -> {
                                Object value = null;
                                try {
                                    value = getFieldValueByName(new InverterResponse(inverter), description.getName());
                                } catch (RuntimeException e) {
                                    System.out.println("Error mapping Description name: " + description.getName());
                                    e.printStackTrace();
                                }
                                return new DescriptionResponse(description, value);
                            })
                            //.filter(response -> response.getValue() != null) // null 값 제거
                            .toList();

                    // Inverter 정보를 Map으로 구성
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", inverter.getId());
                    map.put("name", inverter.getInverterName());
                    map.put("isFault", inverter.getIsFault());
                    map.put("details", descriptionResponses); // Inverter별 고유한 DescriptionResponse 추가
                    return map;
                })
                .toList();

        // 최종 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("category", categoryResponses);
        response.put("columns", inverterResponse);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<String> createInverter(@RequestParam("homeId") Long homeId, @RequestParam("inverterName") String inverterName, @RequestParam("type") String type) {
        inverterService.createInverter(homeId, inverterName, type);
        return ResponseEntity.ok(inverterName + " created");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteInverter(@RequestParam("inverterId") Long inverterId) {
        inverterService.deleteInverter(inverterId);
        return ResponseEntity.ok("Inverter deleted");
    }

    @PutMapping
    public ResponseEntity<String> putInverter(@RequestBody InverterRequest inverterRequest) {
        inverterService.putInverter(inverterRequest);
        return ResponseEntity.ok("Inverter updated");
    }


//    @GetMapping
//    public ResponseEntity<List<Map<String, Object>>> getAll() {
//        List<Inverter> inverters = inverterRepository.findAll();
//
//        List<Map<String, Object>> response = inverters.stream()
//                .map(inverter -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", inverter.getId());
//                    map.put("name", inverter.getInverterName());
//                    map.put("isFault", inverter.getIsFault());
//                    map.put("type", inverter.getType());
//                    map.put("details", new InverterResponse(inverter));
//                    return map;
//                })
//                .toList();
//
//        return ResponseEntity.ok(response);
//    }
}
