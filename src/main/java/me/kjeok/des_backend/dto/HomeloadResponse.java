package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeloadResponse {
    private Long id;
    private String type;
    private String operatingHours;
    private float powerRating;
    private float energyCost;
    private int dailyConsumption;
    private float carbonFootprint;
    private int loadDuration;
    private float powerFactor;
    private String loadPriority;
    private Boolean smartAppliance;
    private Boolean backupPower;
    private Boolean loadFlexibility;
    private Boolean connectedDer;
}