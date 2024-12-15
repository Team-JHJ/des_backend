package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.repository.DescriptionRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;


}
