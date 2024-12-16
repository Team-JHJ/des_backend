package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Vpp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VppRepository extends JpaRepository<Vpp, Long> {
}
