package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kjeok.des_backend.domain.Smartmeter;

@Getter
@AllArgsConstructor
public class SmartmeterResponse {
    private final String installationDate;
    private final Boolean realtimeMonitoring;
    private final String transmissionFrequency;
    private final int energyExported;
    private final int energyImported;
    private final int currentConsumption;

    public SmartmeterResponse(Smartmeter smartmeter) {
        this.installationDate = smartmeter.getInstallationDate();
        this.realtimeMonitoring = smartmeter.getRealtimeMonitoring();
        this.transmissionFrequency = smartmeter.getTransmissionFrequency();
        this.energyExported = smartmeter.getEnergyExported();
        this.energyImported = smartmeter.getEnergyImported();
        this.currentConsumption = smartmeter.getCurrentConsumption();
    }
}