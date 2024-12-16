package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "homeload")
public class Homeload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "operating_hours")
    private String operatingHours;

    @Column(name = "power_rating")
    private float powerRating;

    @Column(name = "energy_cost")
    private float energyCost;

    @Column(name = "daily_consumption")
    private int dailyConsumption;

    @Column(name = "carbon_footprint")
    private float carbonFootprint;

    @Column(name = "load_duration")
    private int loadDuration;

    @Column(name = "power_factor")
    private float powerFactor;

    @Column(name = "load_priority")
    private String loadPriority;

    @Column(name = "smart_appliance")
    private Boolean smartAppliance;

    @Column(name = "backup_power")
    private Boolean backupPower;

    @Column(name = "load_flexibility")
    private Boolean loadFlexibility;

    @Column(name = "connected_der")
    private Boolean connectedDer;

    @Column(name = "is_fault")
    private Boolean isFault;

    @Column(name = "homeload_name")
    private String homeloadName;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
}
