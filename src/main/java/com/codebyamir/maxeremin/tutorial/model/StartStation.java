package com.codebyamir.maxeremin.tutorial.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "start_station")
@NoArgsConstructor
@ToString
public class StartStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    @Getter @Setter private long id;

    @Column(name = "name")
    //@NotEmpty(message = "Please provide a value for the station name")
    @Getter @Setter private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "route", joinColumns = { @JoinColumn(name = "startushka", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "stoptushka", referencedColumnName = "id") })
    @Getter @Setter private Set<StopStation> stopStations;

    @Column(name = "x")
    @Getter @Setter private double x;

    @Column(name = "y")
    @Getter @Setter private double y;

    public StartStation(String name) {
        this.name = name;
    }

    public StartStation(String name, double x, double y, Set<StopStation> stopStations) {
        this.name = name;
        this.stopStations = stopStations;
        this.x = x;
        this.y = y;
    }
}
