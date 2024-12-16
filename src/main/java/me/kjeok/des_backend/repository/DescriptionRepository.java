package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Description;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DescriptionRepository extends JpaRepository<Description, Long> {
    List<Description> findBySource(String source);
}
