package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Description;
import me.kjeok.des_backend.repository.DescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;

    public List<Description> findBySource(String source) {
        return descriptionRepository.findBySource(source);
    }
}
