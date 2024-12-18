package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Homeload;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.dto.CategoryResponse;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.SmartmeterResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.SmartmeterRepository;
import me.kjeok.des_backend.service.DescriptionService;
import me.kjeok.des_backend.service.SmartmeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getDescriptionResponses(@RequestParam("homeId") Long homeId) {

        // Home 객체 조회
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

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

        List<Map<String, Object>> smartmeterResponse = smartmeterList.stream()
                .map(smartmeter -> {
                    List<DescriptionResponse> descriptionResponses = smartmeterService.getDescriptionResponses(home.getId(), "smartmeter_content");

                    Map<String, Object> map = new HashMap<>();
                    map.put("id", smartmeter.getId());
                    map.put("name", smartmeter.getSmartmeterName());
                    map.put("isFault", smartmeter.getIsFault());
                    map.put("details", descriptionResponses); // DescriptionResponse 리스트 추가
                    return map;
                })
                .toList();

        // 최종 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("category", categoryResponses);
        response.put("columns", smartmeterResponse);

        // ResponseEntity로 응답 반환
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> createSmartmeter(@RequestParam("homeId") Long homeId, @RequestParam("smartmeterName") String smartmeterName) {
        smartmeterService.createSmartmeter(homeId, smartmeterName);
        return ResponseEntity.ok("Smartmeter created");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSmartmeter(@RequestParam("id") Long id) {
        smartmeterService.deleteSmartmeter(id);
        return ResponseEntity.ok("Smartmeter deleted");
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
