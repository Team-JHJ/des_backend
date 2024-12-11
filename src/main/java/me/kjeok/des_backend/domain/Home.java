package me.kjeok.des_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "home")
public class Home {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "homename", nullable = false)
    private String homename;

    @Column(name = "is_fault", nullable = false)
    private Boolean isFault = false;

    @ManyToOne
    @JoinColumn(name = "vpp_id", nullable = false) // Home은 반드시 Vpp와 연결되어야 한다고 가정
    private Vpp vpp;

    @ManyToOne
    @JoinColumn(name = "inverter_id", nullable = false) // Home은 반드시 Inverter와 연결되어야 한다고 가정
    private Inverter inverter;

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Der> ders = new ArrayList<>();

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Homeload> homeloads = new ArrayList<>();

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Smartmeter> smartmeters = new ArrayList<>();
}
