package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "inverter")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inverter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "installation_date")
    private String installationDate;

    @Column(name = "efficiency")
    private int efficiency;

    @Column(name = "warranty")
    private int warranty;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "mppt_count")
    private int mpptCount;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "phase_type")
    private String phaseType;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "monitoring")
    private Boolean monitoring;

    @Column(name = "grid_tie")
    private Boolean gridTie;

    @Column(name = "is_fault")
    private Boolean isFault;

    @Column(name = "inverter_name")
    private String inverterName;

    @ManyToOne
    @JoinColumn(name = "vpp_id")
    private Vpp vpp;

    @OneToMany(mappedBy = "inverter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Home> homes;
}
