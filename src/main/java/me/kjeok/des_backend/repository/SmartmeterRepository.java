package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Smartmeter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmartmeterRepository extends JpaRepository<Smartmeter, Long> {
}
