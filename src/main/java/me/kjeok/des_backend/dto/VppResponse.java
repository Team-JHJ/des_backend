package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VppResponse {
    private Long id;
    private int aggregatedCapacity;
    private int availableStorage;
    private int batteryEfficiency;
    private int dispatchableEnergy;
    private int capacityFactor;
    private int forecastedLoad;
    private int responseTime;
    private int renewableShare;
    private int marketRevenue;
    private int sellingAmount;
    private float sellingPrice;
    private Boolean realtimeGrid;
    private Boolean frequencyRegulation;
    private Boolean voltageSupport;
    private Boolean demandResponse;
    private Boolean marketParticipation;
}
