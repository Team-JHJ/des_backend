package me.kjeok.des_backend.service;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.repository.DerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DerService {
    public final DerRepository derRepository;

    public List<Der> findAll() {
        return derRepository.findAll();
    }
}
