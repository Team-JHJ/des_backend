package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HomeloadRequest {
    private final Long id;
    private final String type;
    private final String operatingHours;
    private final float powerRating;
    private final float energyCost;
    private final int dailyComsumption;
    private final float carbonFootprint;
    private final int loadDuration;
    private final float powerFactor;
    private final String loadPriority;
    private final Boolean smartAppliance;
    private final Boolean backupPower;
    private final Boolean loadFlexibility;
    private final Boolean connectedDer;
    private final Boolean isFault;
    private final String homeloadName;
}
