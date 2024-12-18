package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Inverter;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.dto.CategoryResponse;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.InverterResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.InverterRepository;
import me.kjeok.des_backend.service.DescriptionService;
import me.kjeok.des_backend.service.InverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getDescriptionResponses(@RequestParam("homeId") Long homeId) {

        // Home 객체 조회
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        List<Inverter> inverterList = inverterRepository.findByHome(home);
        if (inverterList.isEmpty()) {
            throw new IllegalArgumentException("Not found for the provided homeId: " + homeId);
        }

        // CategoryResponse 조회
        List<CategoryResponse> categoryResponses = descriptionService.getCategoryResponses("inverter_type");

        List<Map<String, Object>> inverterResponse = inverterList.stream()
                .map(inverter -> {
                    List<DescriptionResponse> descriptionResponses = inverterService.getDescriptionResponses(home.getId(), "inverter_content");

                    Map<String, Object> map = new HashMap<>();
                    map.put("id", inverter.getId());
                    map.put("name", inverter.getInverterName());
                    map.put("isFault", inverter.getIsFault());
                    map.put("details", descriptionResponses); // DescriptionResponse 리스트 추가
                    return map;
                })
                .toList();

        // 최종 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("category", categoryResponses);
        response.put("columns", inverterResponse);

        // ResponseEntity로 응답 반환
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
