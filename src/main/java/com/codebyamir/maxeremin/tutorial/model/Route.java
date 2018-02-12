package com.codebyamir.maxeremin.tutorial.model;

import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(name = "luggage")
    private int luggage = 1;

    @Column(name = "coupon")
    private String coupon;

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date = new Date(new Date().getTime());

    @Column(name = "time")
    @DateTimeFormat(pattern = "HH:mm")
    private Date time = new Date(new Date().getTime());

}
