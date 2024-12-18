package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "smartmeter")
@Builder
@AllArgsConstructor
public class Smartmeter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "installation_date")
    private String installationDate;

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

    @Column(name = "smartmeter_name")
    private String smartmeterName;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
}
