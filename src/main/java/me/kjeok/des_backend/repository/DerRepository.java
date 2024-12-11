package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Der;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DerRepository extends JpaRepository<Der, Long> {
}
