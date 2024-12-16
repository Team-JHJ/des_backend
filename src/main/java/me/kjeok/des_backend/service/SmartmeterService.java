package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Description;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Homeload;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.HomeloadResponse;
import me.kjeok.des_backend.dto.SmartmeterResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.SmartmeterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SmartmeterService {
    private final SmartmeterRepository smartmeterRepository;
    private final HomeRepository homeRepository;
    private final DescriptionService descriptionService;

    public List<Smartmeter> findAll() {
        return smartmeterRepository.findAll();
    }

    public Boolean getSmartmeterIsFault(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        List<Smartmeter> smartmeters = smartmeterRepository.findByHome(home);

        if(smartmeters.isEmpty())
            return null;
        else
            return smartmeters.stream().anyMatch(Smartmeter::getIsFault);
    }

    private Object getFieldValueByName(SmartmeterResponse smartmeterResponse, String fieldName) {
        try {
            var field = SmartmeterResponse.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(smartmeterResponse);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }

    public List<DescriptionResponse> getDescriptionResponses(Long homeId, String source) {
        // Home 객체 생성
        Home home = new Home();
        home.setId(homeId);

        Smartmeter smartmeter = smartmeterRepository.findByHome(home)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found"));

        SmartmeterResponse smartmeterResponse = new SmartmeterResponse(smartmeter);

        // Description 조회
        List<Description> descriptions = descriptionService.findBySource(source);
        descriptions.forEach(description -> System.out.println("Description name: " + description.getName()));

        return descriptions.stream()
                .map(description -> {
                    Object value = null;
                    try {
                        value = getFieldValueByName(smartmeterResponse, description.getName());
                    } catch (RuntimeException e) {
                        System.out.println("Error mapping Description name: " + description.getName());
                        e.printStackTrace();
                    }
                    return new DescriptionResponse(description, value);
                })
                .collect(Collectors.toList());
    }
}
