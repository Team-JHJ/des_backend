package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class SmartmeterResponse {
    private Long id;
    private Date installationDate;
    private Boolean realtimeMonitoring;
    private String transmissionFrequency;
    private int energyExported;
    private int energyImported;
    private int currentConsumption;
}