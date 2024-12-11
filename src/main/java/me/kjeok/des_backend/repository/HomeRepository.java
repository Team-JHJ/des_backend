package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Home;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<Home, Long> {
}
