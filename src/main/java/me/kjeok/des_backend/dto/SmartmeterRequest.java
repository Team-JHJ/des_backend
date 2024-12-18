package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SmartmeterRequest {
    private final Long id;
    private final String installationDate;
    private final Boolean realtimeMonitoring;
    private final String transmissionFrequency;
    private final int energyExported;
    private final int energyImported;
    private final int currentConsumption;
    private final Boolean isFault;
    private final String smartmeterName;
}
