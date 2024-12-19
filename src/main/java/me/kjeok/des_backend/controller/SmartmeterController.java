package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Homeload;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.dto.CategoryResponse;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.SmartmeterRequest;
import me.kjeok.des_backend.dto.SmartmeterResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.SmartmeterRepository;
import me.kjeok.des_backend.service.DescriptionService;
import me.kjeok.des_backend.service.SmartmeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/smartmeter")
public class SmartmeterController {
    private final SmartmeterService smartmeterService;
    private final SmartmeterRepository smartmeterRepository;
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

        // Smartmeter 조회 및 기본 Smartmeter 생성
        List<Smartmeter> smartmeterList = smartmeterRepository.findByHome(home);
        if (smartmeterList.isEmpty()) {
            Smartmeter newSmartmeter = Smartmeter.builder()
                    .home(home)
                    .smartmeterName("DefaultSmartmeter")
                    .isFault(false)
                    .build();
            Smartmeter saveSmartmeter = smartmeterRepository.save(newSmartmeter);
            smartmeterList = List.of(saveSmartmeter);
        }

        // CategoryResponse 조회
        List<CategoryResponse> categoryResponses = descriptionService.getCategoryResponses("smartmeter_type");

        // Smartmeter별 DescriptionResponse 생성
        List<Map<String, Object>> smartmeterResponse = smartmeterList.stream()
                .map(smartmeter -> {
                    // Smartmeter별 DescriptionResponse 생성
                    List<DescriptionResponse> descriptionResponses = descriptionService.findBySource("smartmeter_content")
                            .stream()
                            .map(description -> {
                                Object value = null;
                                try {
                                    value = getFieldValueByName(new SmartmeterResponse(smartmeter), description.getName());
                                } catch (RuntimeException e) {
                                    System.out.println("Error mapping Description name: " + description.getName());
                                    e.printStackTrace();
                                }
                                return new DescriptionResponse(description, value);
                            })
                            //.filter(response -> response.getValue() != null) // null 값 제거
                            .toList();

                    // Smartmeter 정보를 Map으로 구성
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", smartmeter.getId());
                    map.put("name", smartmeter.getSmartmeterName());
                    map.put("isFault", smartmeter.getIsFault());
                    map.put("details", descriptionResponses); // Smartmeter별 고유한 DescriptionResponse 추가
                    return map;
                })
                .toList();

        // 최종 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("category", categoryResponses);
        response.put("columns", smartmeterResponse);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<String> createSmartmeter(@RequestParam("homeId") Long homeId, @RequestParam("smartmeterName") String smartmeterName) {
        smartmeterService.createSmartmeter(homeId, smartmeterName);
        return ResponseEntity.ok("Smartmeter created");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSmartmeter(@RequestParam("smartmeterId") Long id) {
        smartmeterService.deleteSmartmeter(id);
        return ResponseEntity.ok("Smartmeter deleted");
    }

    @PutMapping
    public ResponseEntity<String> putSmartmeter(@RequestBody SmartmeterRequest smartmeterRequest) {
        smartmeterService.putSmartmeter(smartmeterRequest);
        return ResponseEntity.ok("Smartmeter updated");
    }

    //    @GetMapping
//    public ResponseEntity<List<Map<String, Object>>> getAll() {
//        List<Smartmeter> smartmeters = smartmeterService.findAll();
//
//        List<Map<String, Object>> response = smartmeters.stream()
//                .map(smartmeter -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", smartmeter.getId());
//                    map.put("name", smartmeter.getSmartmeterName());
//                    map.put("isFault", smartmeter.getIsFault());
//                    map.put("details", new SmartmeterResponse(smartmeter));
//                    return map;
//                })
//                .toList();
//
//        return ResponseEntity.ok(response);
//    }
}
