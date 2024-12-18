package me.kjeok.des_backend.dto;

import lombok.Getter;
import me.kjeok.des_backend.domain.Inverter;

@Getter
public class InverterResponse {
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

    public InverterResponse(Inverter inverter) {
        this.installationDate = inverter.getInstallationDate();
        this.efficiency = inverter.getEfficiency();
        this.warranty = inverter.getWarranty();
        this.capacityFactor = inverter.getCapacityFactor();
        this.mpptCount = inverter.getMpptCount();
        this.manufacturer = inverter.getManufacturer();
        this.model = inverter.getModel();
        this.phaseType = inverter.getPhaseType();
        this.status = inverter.getStatus();
        this.monitoring = inverter.getMonitoring();
        this.gridTie = inverter.getGridTie();
    }
}
