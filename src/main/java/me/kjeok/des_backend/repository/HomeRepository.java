package me.kjeok.des_backend.repository;

import me.kjeok.des_backend.domain.Home;
import me.kjeok.des_backend.domain.Homeload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeRepository extends JpaRepository<Home, Long> {

}
