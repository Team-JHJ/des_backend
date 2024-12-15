package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Description;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, Long> {
}
