package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Description;
import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Homeload;
import me.kjeok.des_backend.dto.DescriptionResponse;
import me.kjeok.des_backend.dto.HomeloadRequest;
import me.kjeok.des_backend.dto.HomeloadResponse;
import me.kjeok.des_backend.repository.HomeRepository;
import me.kjeok.des_backend.repository.HomeloadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HomeloadService {
    private final HomeloadRepository homeloadRepository;
    private final HomeRepository homeRepository;
    private final DescriptionService descriptionService;

    public List<Homeload> findAll() {
        return homeloadRepository.findAll();
    }

    public Boolean getHomeloadIsFault(Long homeId) {
        // Home 조회
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        List<Homeload> homeloads = homeloadRepository.findByHome(home);
        if (homeloads.isEmpty())
            return null;
        else
            return homeloads.stream().anyMatch(Homeload::getIsFault);
    }

    private Object getFieldValueByName(HomeloadResponse homeloadResponse, String fieldName) {
        try {
            var field = HomeloadResponse.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(homeloadResponse);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }

    public List<DescriptionResponse> getDescriptionResponses(Long homeId, String source) {
        // Home 객체 생성
        Home home = new Home();
        home.setId(homeId);

        Homeload homeload = homeloadRepository.findByHome(home)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Homeload not found"));

        HomeloadResponse homeloadResponse = new HomeloadResponse(homeload);

        // Description 조회
        List<Description> descriptions = descriptionService.findBySource(source);
        descriptions.forEach(description -> System.out.println("Description name: " + description.getName()));

        return descriptions.stream()
                .map(description -> {
                    Object value = null;
                    try {
                        value = getFieldValueByName(homeloadResponse, description.getName());
                    } catch (RuntimeException e) {
                        System.out.println("Error mapping Description name: " + description.getName());
                        e.printStackTrace();
                    }
                    return new DescriptionResponse(description, value);
                })
                .collect(Collectors.toList());
    }

    public void createHomeload(Long homeId, String homeloadName, String type) {
        Home home = homeRepository.findById(homeId)
                .orElseThrow(() -> new IllegalArgumentException("Home not found"));

        Homeload homeload = new Homeload();
        homeload.setHome(home);
        homeload.setHomeloadName(homeloadName);
        homeload.setType(type);
        homeload.setIsFault(false);

        homeloadRepository.save(homeload);
    }

    public void deleteHomeload(Long homeloadId) {
        homeloadRepository.deleteById(homeloadId);
    }

    public List<HomeloadResponse> putHomeload(HomeloadRequest homeloadRequest) {
        Homeload homeload = homeloadRepository.findById(homeloadRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Homeload not found with id: " + homeloadRequest.getId()));

        BeanUtils.copyProperties(homeloadRequest, homeload);
        homeloadRepository.save(homeload);

        return homeloadRepository.findById(homeloadRequest.getId())
                .stream()
                .map(HomeloadResponse::new)
                .collect(Collectors.toList());
    }
}