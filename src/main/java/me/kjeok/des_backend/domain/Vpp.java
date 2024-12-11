package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vpp")
public class Vpp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "aggregated_capacity")
    private int aggregatedCapacity;

    @Column(name = "available_storage")
    private int availableStorage;

    @Column(name = "battery_efficiency")
    private int batteryEfficiency;

    @Column(name = "dispatchable_energy")
    private int dispatchableEnergy;

    @Column(name = "capacity_factor")
    private int capacityFactor;

    @Column(name = "forecasted_load")
    private int forecastedLoad;

    @Column(name = "response_time")
    private int responseTime;

    @Column(name = "renewable_share")
    private int renewableShare;

    @Column(name = "market_revenue")
    private int marketRevenue;

    @Column(name = "selling_amount")
    private int sellingAmount;

    @Column(name = "selling_price")
    private float sellingPrice;

    @Column(name = "realtime_grid")
    private Boolean realtimeGrid;

    @Column(name = "frequency_regulation")
    private Boolean frequencyRegulation;

    @Column(name = "voltage_support")
    private Boolean voltageSupport;

    @Column(name = "demand_response")
    private Boolean demandResponse;

    @Column(name = "market_participation")
    private Boolean marketParticipation;

    @Column(name = "is_fault")
    private Boolean isFault;

    @OneToMany(mappedBy = "vpp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Home> homes;
}
