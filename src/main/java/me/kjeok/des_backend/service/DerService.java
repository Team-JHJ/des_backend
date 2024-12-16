package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.domain.Description;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.dto.DerResponse;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.repository.DerRepository;
import me.kjeok.des_backend.repository.DescriptionRepository;
import me.kjeok.des_backend.repository.HomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DerService {
    private final DerRepository derRepository;
    private final HomeRepository homeRepository;
    private final DescriptionService descriptionService;
    private final DescriptionRepository descriptionRepository;

    public List<Der> findAll() {
        return derRepository.findAll();
    }

    public Boolean getDerIsFault(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        List<Der> ders = derRepository.findByHome(home);

        if(ders.isEmpty())
            return null;
        else
            return ders.stream().anyMatch(Der::getIsFault);
    }

    public List<Der> findByHomeAndType(Home home, String type) {
        return derRepository.findByHomeAndType(home, type);
    }

    private Object getFieldValueByName(DerResponse derResponse, String fieldName) {
        try {
            // DerResponse 클래스에서 fieldName에 해당하는 필드 가져오기
            var field = DerResponse.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(derResponse);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }

    public List<DescriptionResponse> getDescriptionResponses(Long homeId, String type, String derName) {
        // Home 객체 생성
        Home home = new Home();
        home.setId(homeId);

        // Der 조회
        Der der = derRepository.findByHomeAndTypeAndDerName(home, type, derName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Der not found"));

        // DerResponse로 변환
        DerResponse derResponse = new DerResponse(der);

        // Description 조회
        List<Description> descriptions = descriptionService.findBySource("der_content");
        descriptions.forEach(description -> System.out.println("Description name: " + description.getName()));

        // DescriptionResponse로 변환
        return descriptions.stream()
                .map(description -> {
                    Object value = null;
                    try {
                        value = getFieldValueByName(derResponse, description.getName());
                    } catch (RuntimeException e) {
                        System.out.println("Error mapping Description name: " + description.getName());
                        e.printStackTrace();
                    }
                    return new DescriptionResponse(description, value);
                })
                .collect(Collectors.toList());
    }

    public void deleteDer(Long id) {
        derRepository.deleteById(id);
    }
}