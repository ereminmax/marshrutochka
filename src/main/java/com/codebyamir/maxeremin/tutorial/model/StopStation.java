package com.codebyamir.maxeremin.tutorial.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stop_station")
@NoArgsConstructor
@ToString
public class StopStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    @Getter @Setter private long id;

    @Column(name = "name")
    //@NotEmpty(message = "Please provide a value for the station name")
    @Getter @Setter private String name;

    @ManyToMany(mappedBy = "stopStations", fetch = FetchType.LAZY)
    @Getter @Setter private Set<StartStation> startStations;

    @Column(name = "x")
    @Getter @Setter private double x;

    @Column(name = "y")
    @Getter @Setter private double y;

    public StopStation(String name) {
        this.name = name;
    }

    public StopStation(String name, double x, double y, Set<StartStation> startStations) {
        this.name = name;
        this.startStations = startStations;
        this.x = x;
        this.y = y;
    }
}
