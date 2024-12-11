package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class DerResponse {
    private Long id;
    private String type;
    private Date installationDate;
    private String location;
    private int generationCapacity;
    private int storageCapacity;
    private int efficiency;
    private float soc;
    private int energyGeneration;
    private Boolean gridConnection;
    private int installationCosts;
    private int omCosts;
    private int paybackPeriod;
    private int energySavings;
    private int roi;
    private int incentives;
    private int co2Saved;
    private float carbonIntensity;
    private int renewableShare;
    private int carbonFootprint;
}
