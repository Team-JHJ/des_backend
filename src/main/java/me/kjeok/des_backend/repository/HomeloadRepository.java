package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Homeload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeloadRepository extends JpaRepository<Homeload, Long> {
}
