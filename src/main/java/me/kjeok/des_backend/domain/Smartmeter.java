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
    private String installationDate = "";

    @Column(name = "realtime_monitoring")
    private Boolean realtimeMonitoring = false;

    @Column(name = "transmission_frequency")
    private String transmissionFrequency = "";

    @Column(name = "energy_exported")
    private int energyExported = 0;

    @Column(name = "energy_imported")
    private int energyImported = 0;

    @Column(name = "current_consumption")
    private int currentConsumption = 0;

    @Column(name = "is_fault")
    private Boolean isFault = false;

    @Column(name = "smartmeter_name")
    private String smartmeterName = "";

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
}
