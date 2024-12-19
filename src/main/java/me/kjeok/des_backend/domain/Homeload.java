package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "homeload")
@AllArgsConstructor
@Builder
public class Homeload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "type")
    private String type = "";

    @Column(name = "operating_hours")
    private String operatingHours = "";

    @Column(name = "power_rating")
    private float powerRating = 0;

    @Column(name = "energy_cost")
    private float energyCost = 0;

    @Column(name = "daily_consumption")
    private int dailyConsumption = 0;

    @Column(name = "carbon_footprint")
    private float carbonFootprint = 0;

    @Column(name = "load_duration")
    private int loadDuration = 0;

    @Column(name = "power_factor")
    private float powerFactor = 0;

    @Column(name = "load_priority")
    private String loadPriority = "";

    @Column(name = "smart_appliance")
    private Boolean smartAppliance = false;

    @Column(name = "backup_power")
    private Boolean backupPower = false;

    @Column(name = "load_flexibility")
    private Boolean loadFlexibility = false;

    @Column(name = "connected_der")
    private Boolean connectedDer = false;

    @Column(name = "is_fault")
    private Boolean isFault = false;

    @Column(name = "homeload_name")
    private String homeloadName = "";

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
}
