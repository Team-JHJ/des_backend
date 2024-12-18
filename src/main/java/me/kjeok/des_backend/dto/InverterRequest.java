package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InverterRequest {
    private final Long id;
    private final String type;
    private final String installationDate;
    private final int efficiency;
    private final int warranty;
    private final int capacityFactor;
    private final int mpptCount;
    private final String manufacturer;
    private final String model;
    private final String phaseType;
    private final Boolean status;
    private final Boolean monitoring;
    private final Boolean gridTie;
    private final Boolean isFault;
    private final String inverterName;
}
