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

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getDescriptionResponses(@RequestParam("homeId") Long homeId) {

        // Home 객체 조회
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        // Home에 속한 모든 DER 조회
        List<Der> derList = derRepository.findByHome(home);
        if (derList.isEmpty()) {
            // Der 생성
            Der newDer = Der.builder()
                    .home(home)
                    .type("Solar")
                    .derName("DefaultDer")
                    .battery(0)
                    .isFault(false)
                    .build();
            Der savedDer = derRepository.save(newDer);
            derList = List.of(savedDer); // 새로운 Der를 리스트로 대체
        }

        // CategoryResponse 조회
        List<CategoryResponse> categoryResponses = descriptionService.getCategoryResponses("der_type");

        // 각 DER의 DescriptionResponse 생성 및 결합
        List<Map<String, Object>> derResponse = derList.stream()
                .map(der -> {
                    // 해당 DER에 대한 DescriptionResponse 생성
                    List<DescriptionResponse> descriptionResponses = derService.getDescriptionResponses(home.getId(), "der_content");

                    // DER 데이터와 DescriptionResponse를 결합
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

        // 최종 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("category", categoryResponses);
        response.put("columns", derResponse);

        // ResponseEntity로 응답 반환
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
