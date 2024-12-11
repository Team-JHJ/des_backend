package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "smartmeter")
public class Smartmeter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "installation_date")
    private Date installationDate;

    @Column(name = "realtime_monitoring")
    private Boolean realtimeMonitoring;

    @Column(name = "transmission_frequency")
    private String transmissionFrequency;

    @Column(name = "energy_exported")
    private int energyExported;

    @Column(name = "energy_imported")
    private int energyImported;

    @Column(name = "current_consumption")
    private int currentConsumption;

    @Column(name = "is_fault")
    private Boolean isFault;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
}
