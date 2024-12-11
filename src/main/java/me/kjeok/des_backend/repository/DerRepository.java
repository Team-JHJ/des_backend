package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Der;
import me.kjeok.des_backend.domain.Home;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DerRepository extends JpaRepository<Der, Long> {
    List<Der> findByHome(Home home);
}
