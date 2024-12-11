package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class InverterResponse {
    private Long id;
    private Date installationDate;
    private int efficiency;
    private int warranty;
    private int capacity;
    private int mpptCount;
    private String manufacturer;
    private String model;
    private int phaseType;
    private Boolean status;
    private Boolean monitoring;
    private Boolean gridTie;
}
