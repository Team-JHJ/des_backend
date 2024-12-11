package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "der")
public class Der {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "installation_date")
    private Date installationDate;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "generation_capacity", nullable = false)
    private int generationCapacity;

    @Column(name = "storage_capacity")
    private int storageCapacity;

    @Column(name = "efficiency")
    private int efficiency;

    @Column(name = "soc")
    private int soc;

    @Column(name = "energy_generation")
    private int energyGeneration;

    @Column(name = "grid_connection")
    private Boolean gridConnection;

    @Column(name = "installation_costs")
    private int installationCosts;

    @Column(name = "om_costs")
    private int omCosts;

    @Column(name = "payback_period")
    private int paybackPeriod;

    @Column(name = "energy_savings")
    private int energySavings;

    @Column(name = "roi")
    private int roi;

    @Column(name = "incentives")
    private int incentives;

    @Column(name = "co2_saved")
    private int co2Saved;

    @Column(name = "carbon_intensity")
    private int carbonIntensity;

    @Column(name = "renewable_share")
    private int renewableShare;

    @Column(name = "is_fault", nullable = false)
    private Boolean isFault = false;

    @ManyToOne
    @JoinColumn(name = "home_id", nullable = false)
    private Home home;

    @Column(name = "battery")
    private int battery;
}
