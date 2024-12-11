package me.kjeok.des_backend.dto;

import lombok.Getter;
import me.kjeok.des_backend.domain.Homeload;

@Getter
public class HomeloadResponse {
    private final Long id;
    private final String type;
    private final String operatingHours;
    private final float powerRating;
    private final float energyCost;
    private final int dailyConsumption;
    private final float carbonFootprint;
    private final int loadDuration;
    private final float powerFactor;
    private final String loadPriority;
    private final Boolean smartAppliance;
    private final Boolean backupPower;
    private final Boolean loadFlexibility;
    private final Boolean connectedDer;

    public HomeloadResponse(Homeload homeload) {
        this.id = homeload.getId();
        this.type = homeload.getType();
        this.operatingHours = homeload.getOperatingHours();
        this.powerRating = homeload.getPowerRating();
        this.energyCost = homeload.getEnergyCost();
        this.dailyConsumption = homeload.getDailyConsumption();
        this.carbonFootprint = homeload.getCarbonFootprint();
        this.loadDuration = homeload.getLoadDuration();
        this.powerFactor = homeload.getPowerFactor();
        this.loadPriority = homeload.getLoadPriority();
        this.smartAppliance = homeload.getSmartAppliance();
        this.backupPower = homeload.getBackupPower();
        this.loadFlexibility = homeload.getLoadFlexibility();
        this.connectedDer = homeload.getConnectedDer();
    }
}