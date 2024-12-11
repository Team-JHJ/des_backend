package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Inverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InverterRepository extends JpaRepository<Inverter, Long> {
    List<Inverter> findByHome(Home home);
}
