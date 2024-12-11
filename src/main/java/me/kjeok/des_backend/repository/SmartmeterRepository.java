package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Smartmeter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmartmeterRepository extends JpaRepository<Smartmeter, Long> {
    List<Smartmeter> findByHome(Home home);
}
