package com.codebyamir.maxeremin.tutorial.model;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "station")
@NoArgsConstructor
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    @Getter @Setter private long id;

    @Column(name = "name")
    //@NotEmpty(message = "Please provide a value for the station name")
    @Getter @Setter private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "route", joinColumns = { @JoinColumn(name = "start_station_id") }, inverseJoinColumns = { @JoinColumn(name = "stop_station_id") })
    @Getter @Setter private Set<Station> startStations = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "startStations")
    @Getter @Setter private Set<Station> stopStations = new LinkedHashSet<>();

    @Column(name = "x")
    @Getter @Setter private double x;

    @Column(name = "y")
    @Getter @Setter private double y;

    public Station(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

}
