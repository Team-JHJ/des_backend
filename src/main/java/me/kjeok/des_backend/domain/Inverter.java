package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inverter")
@Getter
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class Inverter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type = "";

    @Column(name = "installation_date", nullable = false)
    private String installationDate = "";

    @Column(name = "efficiency", nullable = false)
    private int efficiency = 0;

    @Column(name = "warranty", nullable = false)
    private int warranty = 0;

    @Column(name = "capacity_factor", nullable = false)
    private int capacityFactor = 0;

    @Column(name = "mppt_count", nullable = false)
    private int mpptCount = 0;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer = "";

    @Column(name = "model", nullable = false)
    private String model = "";

    @Column(name = "phase_type", nullable = false)
    private String phaseType = "";

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @Column(name = "monitoring", nullable = false)
    private Boolean monitoring = false;

    @Column(name = "grid_tie", nullable = false)
    private Boolean gridTie = false;

    @Column(name = "is_fault", nullable = false)
    private Boolean isFault = false;

    @Column(name = "inverter_name", nullable = false)
    private String inverterName = "";

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;
}
