package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Description;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Inverter;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.InverterResponse;
import me.kjeok.des_backend.dto.SmartmeterResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.InverterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InverterService {
    private final InverterRepository inverterRepository;
    private final HomeRepository homeRepository;
    private final DescriptionService descriptionService;

    public List<Inverter> findAll() {
        return inverterRepository.findAll();
    }

    public Boolean getInverterIsFault(Long homeId) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        List<Inverter> inverters = inverterRepository.findByHome(home);

        if(inverters.isEmpty())
            return null;
        else
            return inverters.stream().anyMatch(Inverter::getIsFault);
    }

    private Object getFieldValueByName(InverterResponse inverterResponse, String fieldName) {
        try {
            var field = InverterResponse.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(inverterResponse);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }

    public List<DescriptionResponse> getDescriptionResponses(Long homeId, String source) {
        // Home 객체 생성
        Home home = new Home();
        home.setId(homeId);

        Inverter inverter = inverterRepository.findByHome(home)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found"));

        InverterResponse inverterResponse = new InverterResponse(inverter);

        // Description 조회
        List<Description> descriptions = descriptionService.findBySource(source);
        descriptions.forEach(description -> System.out.println("Description name: " + description.getName()));

        return descriptions.stream()
                .map(description -> {
                    Object value = null;
                    try {
                        value = getFieldValueByName(inverterResponse, description.getName());
                    } catch (RuntimeException e) {
                        System.out.println("Error mapping Description name: " + description.getName());
                        e.printStackTrace();
                    }
                    return new DescriptionResponse(description, value);
                })
                .collect(Collectors.toList());
    }
}
