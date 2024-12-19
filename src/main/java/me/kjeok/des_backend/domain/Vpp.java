package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "vpp")
@Builder
@AllArgsConstructor
public class Vpp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "aggregated_capacity")
    private int aggregatedCapacity = 0;

    @Column(name = "available_storage")
    private int availableStorage = 0;

    @Column(name = "battery_efficiency")
    private int batteryEfficiency = 0;

    @Column(name = "dispatchable_energy")
    private int dispatchableEnergy = 0;

    @Column(name = "capacity_factor")
    private int capacityFactor = 0;

    @Column(name = "forecasted_load")
    private int forecastedLoad = 0;

    @Column(name = "response_time")
    private int responseTime = 0;

    @Column(name = "renewable_share")
    private int renewableShare = 0;

    @Column(name = "market_revenue")
    private int marketRevenue = 0;

    @Column(name = "selling_amount")
    private int sellingAmount = 0;

    @Column(name = "selling_price")
    private float sellingPrice = 0;

    @Column(name = "realtime_grid")
    private Boolean realtimeGrid = false;

    @Column(name = "frequency_regulation")
    private Boolean frequencyRegulation = false;

    @Column(name = "voltage_support")
    private Boolean voltageSupport = false;

    @Column(name = "demand_response")
    private Boolean demandResponse = false;

    @Column(name = "market_participation")
    private Boolean marketParticipation = false;

    @Column(name = "is_fault")
    private Boolean isFault = false;

    @OneToMany(mappedBy = "vpp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Home> homes;
}
