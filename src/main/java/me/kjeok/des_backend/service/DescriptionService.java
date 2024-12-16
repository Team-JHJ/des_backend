package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Description;
import me.kjeok.des_backend.dto.CategoryResponse;
import me.kjeok.des_backend.repository.DescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;

    public List<Description> findBySource(String source) {
        return descriptionRepository.findBySource(source);
    }

    public List<CategoryResponse> getCategoryResponses(String category) {
        // Description 조회
        List<Description> descriptions = findBySource(category);

        return descriptions.stream()
                .map(description -> new CategoryResponse(description.getName(), description.getDescription()))
                .collect(Collectors.toList());
    }
}
