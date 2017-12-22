package com.codebyamir.maxeremin.tutorial.model;

import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "from_station_id")
    private long fromStationName;

    @Column(name = "to_station_id")
    private long toStationName;

    @Column(name = "number_of_seats")
    private int numberOfSeats = 1;

    @Column(name = "time")
    private Date time = getTime();
}
