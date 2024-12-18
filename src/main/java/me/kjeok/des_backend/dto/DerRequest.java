package me.kjeok.des_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DerRequest {
    private final Long id;
    private final String derName;
    private final String type;
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
    private final Boolean isFault;
    private final int battery;
}
