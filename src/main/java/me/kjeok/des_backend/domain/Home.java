package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "home")
public class Home {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "home_name", nullable = false)
    private String homeName;

    @Column(name = "inverter_fault")
    private Boolean inverterFault = false;

    @Column(name = "der_fault")
    private Boolean derFault = false;

    @Column(name = "homeload_fault")
    private Boolean homeloadFault = false;

    @Column(name = "smartmeter_fault")
    private Boolean smartmeterFault = false;

    @ManyToOne
    @JoinColumn(name = "vpp_id", nullable = false) // Home은 반드시 Vpp와 연결되어야 한다고 가정
    private Vpp vpp;

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Der> ders = new ArrayList<>();

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Homeload> homeloads = new ArrayList<>();

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Smartmeter> smartmeters = new ArrayList<>();

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inverter> inverters = new ArrayList<>();
}
