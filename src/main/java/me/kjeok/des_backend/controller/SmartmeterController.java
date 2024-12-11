package me.kjeok.des_backend.controller;

import lombok.AllArgsConstructor;
import me.kjeok.des_backend.domain.Smartmeter;
import me.kjeok.des_backend.dto.SmartmeterResponse;
import me.kjeok.des_backend.service.SmartmeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SmartmeterController {
    private final SmartmeterService smartmeterService;

}
