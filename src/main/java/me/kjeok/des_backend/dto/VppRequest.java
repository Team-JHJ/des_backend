package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VppRequest {
    private final Long id;
    private final int aggregatedCapacity;
    private final int availableStorage;
    private final int batteryEfficiency;
    private final int dispatchableEnergy;
    private final int capacityFactor;
    private final int forecastedLoad;
    private final int responseTime;
    private final int renewableShare;
    private final int marketRevenue;
    private final int sellingAmount;
    private final double sellingPrice;
    private final boolean realtimeGrid;
    private final boolean frequencyRegulation;
    private final boolean voltageSupport;
    private final boolean demandResponse;
    private final boolean marketParticipation;
    private final boolean isFault;
    private final String vppName;
}
