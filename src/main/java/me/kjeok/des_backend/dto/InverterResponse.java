package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kjeok.des_backend.domain.Inverter;

import java.util.Date;

@Getter
public class InverterResponse {
    private final Long id;
    private final Date installationDate;
    private final int efficiency;
    private final int warranty;
    private final int capacity;
    private final int mpptCount;
    private final String manufacturer;
    private final String model;
    private final String phaseType;
    private final Boolean status;
    private final Boolean monitoring;
    private final Boolean gridTie;

    public InverterResponse(Inverter inverter) {
        this.id = inverter.getId();
        this.installationDate = inverter.getInstallationDate();
        this.efficiency = inverter.getEfficiency();
        this.warranty = inverter.getWarranty();
        this.capacity = inverter.getCapacity();
        this.mpptCount = inverter.getMpptCount();
        this.manufacturer = inverter.getManufacturer();
        this.model = inverter.getModel();
        this.phaseType = inverter.getPhaseType();
        this.status = inverter.getStatus();
        this.monitoring = inverter.getMonitoring();
        this.gridTie = inverter.getGridTie();
    }
}
