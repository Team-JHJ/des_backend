package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Inverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InverterRepository extends JpaRepository<Inverter, Long> {
}
