package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "der")
public class Der {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "type", length = 50, nullable = false)
    private String type = "";

    @Column(name = "installation_date")
    private String installationDate = "";

    @Column(name = "location", length = 255)
    private String location = "";

    @Column(name = "generation_capacity", nullable = false)
    private int generationCapacity = 0;

    @Column(name = "storage_capacity")
    private int storageCapacity = 0;

    @Column(name = "efficiency")
    private int efficiency = 0;

    @Column(name = "soc")
    private float soc = 0;

    @Column(name = "energy_generation")
    private int energyGeneration = 0;

    @Column(name = "grid_connection")
    private Boolean gridConnection = false;

    @Column(name = "installation_costs")
    private int installationCosts = 0;

    @Column(name = "om_costs")
    private int omCosts = 0;

    @Column(name = "payback_period")
    private int paybackPeriod = 0;

    @Column(name = "energy_savings")
    private int energySavings = 0;

    @Column(name = "roi")
    private int roi = 0;

    @Column(name = "incentives")
    private int incentives = 0;

    @Column(name = "co2_saved")
    private int co2Saved = 0;

    @Column(name = "carbon_intensity")
    private float carbonIntensity = 0;

    @Column(name = "renewable_share")
    private int renewableShare = 0;

    @Column(name = "carbon_footprint")
    private int carbonFootprint = 0;

    @Column(name = "is_fault", nullable = false)
    private Boolean isFault = false;

    @ManyToOne
    @JoinColumn(name = "home_id", nullable = false)
    private Home home;

    @Column(name = "battery")
    private int battery = 0;

    @Column(name = "der_name")
    private String derName = "";
}
