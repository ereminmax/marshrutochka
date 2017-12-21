package com.codebyamir.maxeremin.tutorial.service;

import com.codebyamir.maxeremin.tutorial.model.Station;
import com.codebyamir.maxeremin.tutorial.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {
    private StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public void saveStations(Iterable<Station> stations) {
        stationRepository.save(stations);
    }

    public Iterable<Station> findAll() {
        return stationRepository.findAll();
    }
}
