package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Description;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Vpp;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.InverterResponse;
import me.kjeok.des_backend.dto.VppResponse;
import me.kjeok.des_backend.repository.VppRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VppService {
    private final VppRepository vppRepository;
    private final DescriptionService descriptionService;

    private Object getFieldValueByName(VppResponse vppResponse, String fieldName) {
        try {
            var field = VppResponse.class.getDeclaredField(fieldName); // VppResponse 필드 접근
            field.setAccessible(true);
            return field.get(vppResponse); // 필드 값 반환
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName, e); // 명확한 예외 메시지
        }
    }

    public List<DescriptionResponse> getDescriptionResponses(Long id, String source) {
        // Vpp 조회
        Vpp vpp = vppRepository.findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Vpp not found for ID: " + id));

        VppResponse vppResponse = new VppResponse(vpp);

        // Description 조회
        List<Description> descriptions = descriptionService.findBySource(source);
        if (descriptions.isEmpty()) {
            throw new IllegalArgumentException("No descriptions found for source: " + source);
        }

        // Description과 VppResponse 매핑
        return descriptions.stream()
                .map(description -> {
                    Object value;
                    try {
                        value = getFieldValueByName(vppResponse, description.getName()); // 필드 값 조회
                    } catch (IllegalArgumentException e) {
                        // 특정 Description과 매핑 실패 시 기본 값 처리
                        System.err.println("Field mapping failed for Description: " + description.getName());
                        e.printStackTrace();
                        value = null; // 기본값 처리
                    }
                    return new DescriptionResponse(description, value);
                })
                .collect(Collectors.toList());
    }

    public void deleteVpp(Long id) {
        vppRepository.deleteById(id);
    }

    public void createVpp() {
        Vpp vpp = new Vpp();
        vppRepository.save(vpp);
    }
}
