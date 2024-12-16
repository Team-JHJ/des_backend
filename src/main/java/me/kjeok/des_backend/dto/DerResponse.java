package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kjeok.des_backend.domain.Der;

@Getter
@AllArgsConstructor
public class DerResponse {
    private final String installationDate;
    private final String location;
    private final int generationCapacity;
    private final int storageCapacity;
    private final int efficiency;
    private final float soc;
    private final int energyGeneration;
    private final Boolean gridConnection;
    private final int installationCosts;
    private final int omCosts;
    private final int paybackPeriod;
    private final int energySavings;
    private final int roi;
    private final int incentives;
    private final int co2Saved;
    private final float carbonIntensity;
    private final int renewableShare;
    private final int carbonFootprint;

    public DerResponse(Der der) {
        this.installationDate = der.getInstallationDate();
        this.location = der.getLocation();
        this.generationCapacity = der.getGenerationCapacity();
        this.storageCapacity = der.getStorageCapacity();
        this.efficiency = der.getEfficiency();
        this.soc = der.getSoc();
        this.energyGeneration = der.getEnergyGeneration();
        this.gridConnection = der.getGridConnection();
        this.installationCosts = der.getInstallationCosts();
        this.omCosts = der.getOmCosts();
        this.paybackPeriod = der.getPaybackPeriod();
        this.energySavings = der.getEnergySavings();
        this.roi = der.getRoi();
        this.incentives = der.getIncentives();
        this.co2Saved = der.getCo2Saved();
        this.carbonIntensity = der.getCarbonIntensity();
        this.renewableShare = der.getRenewableShare();
        this.carbonFootprint = der.getCarbonFootprint();
    }
}
