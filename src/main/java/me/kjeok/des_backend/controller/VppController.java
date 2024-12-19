package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Inverter;
import me.kjeok.des_backend.domain.Vpp;
import me.kjeok.des_backend.dto.CategoryResponse;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.VppRequest;
import me.kjeok.des_backend.dto.VppResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.VppRepository;
import me.kjeok.des_backend.service.DescriptionService;
import me.kjeok.des_backend.service.VppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vpp")
public class VppController {
    private final VppRepository vppRepository;
    private final HomeRepository homeRepository;
    private final DescriptionService descriptionService;
    private final VppService vppService;



    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getDescriptionResponses(@RequestParam("vppId") Long id) {

        // Home 객체 조회
        Home home = homeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Home not found with ID: " + id));

        // Vpp 객체 조회 (단일 객체)
        Vpp vpp = vppRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vpp not found with ID: " + id));

        if (vpp == null) {
            Vpp newVpp = Vpp.builder()
                    .aggregatedCapacity(0) // 기본값
                    .availableStorage(0) // 기본값
                    .batteryEfficiency(0) // 기본값
                    .dispatchableEnergy(0) // 기본값
                    .capacityFactor(0) // 기본값
                    .forecastedLoad(0) // 기본값
                    .responseTime(0) // 기본값
                    .renewableShare(0) // 기본값
                    .marketRevenue(0) // 기본값
                    .sellingAmount(0) // 기본값
                    .sellingPrice(0.0f) // 기본값
                    .realtimeGrid(false) // 기본값
                    .frequencyRegulation(false) // 기본값
                    .voltageSupport(false) // 기본값
                    .demandResponse(false) // 기본값
                    .marketParticipation(false) // 기본값
                    .isFault(false) // 기본값
                    .build();

            vppRepository.save(newVpp);
        }


        // CategoryResponse 조회
        List<CategoryResponse> categoryResponses = descriptionService.getCategoryResponses("vpp_type");

        // VppResponse 매핑 (단일 객체에 대해 처리)
        List<DescriptionResponse> descriptionResponses = vppService.getDescriptionResponses(vpp.getId(), "vpp_content");

        // Vpp 데이터와 DescriptionResponse 결합
        Map<String, Object> vppDetails = new HashMap<>();
        vppDetails.put("id", vpp.getId());
        vppDetails.put("isFault", vpp.getIsFault());
        vppDetails.put("details", descriptionResponses); // DescriptionResponse 리스트 추가

        // 최종 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("category", categoryResponses);
        response.put("columns", vppDetails);

        // ResponseEntity로 응답 반환
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> createVpp() {
        vppService.createVpp();
        return ResponseEntity.ok("Vpp created");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteVpp(@RequestParam("vppId") Long id) {
        vppService.deleteVpp(id);
        return ResponseEntity.ok("Vpp deleted");
    }

    @PutMapping
    public ResponseEntity<String> updateVpp(@RequestBody VppRequest vppRequest) {
        vppService.putVpp(vppRequest);
        return ResponseEntity.ok("Vpp updated");
    }

//    @GetMapping
//    public ResponseEntity<List<Map<String, Object>>> getAll() {
//        List<Vpp> vpps = vppRepository.findAll();
//
//        List<Map<String, Object>> response = vpps.stream()
//                .map(vpp -> {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", vpp.getId());
//                    map.put("isFault", vpp.getIsFault());
//                    map.put("details", new VppResponse(vpp));
//                    return map;
//                })
//                .toList();
//        return ResponseEntity.ok(response);
//    }

}
