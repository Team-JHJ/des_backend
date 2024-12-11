package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kjeok.des_backend.domain.Vpp;

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

    public VppResponse(Vpp vpp) {
        this.id = vpp.getId();
        this.aggregatedCapacity = vpp.getAggregatedCapacity();
        this.availableStorage = vpp.getAvailableStorage();
        this.batteryEfficiency = vpp.getBatteryEfficiency();
        this.dispatchableEnergy = vpp.getDispatchableEnergy();
        this.capacityFactor = vpp.getCapacityFactor();
        this.forecastedLoad = vpp.getForecastedLoad();
        this.responseTime = vpp.getResponseTime();
        this.renewableShare = vpp.getRenewableShare();
        this.marketRevenue = vpp.getMarketRevenue();
        this.sellingAmount = vpp.getSellingAmount();
        this.sellingPrice = vpp.getSellingPrice();
        this.realtimeGrid = vpp.getRealtimeGrid();
        this.frequencyRegulation = vpp.getFrequencyRegulation();
        this.voltageSupport = vpp.getVoltageSupport();
        this.demandResponse = vpp.getDemandResponse();
        this.marketParticipation = vpp.getMarketParticipation();
    }
}
