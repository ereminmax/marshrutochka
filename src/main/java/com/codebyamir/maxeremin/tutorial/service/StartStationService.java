package com.codebyamir.maxeremin.tutorial.service;

import com.codebyamir.maxeremin.tutorial.model.StartStation;
import com.codebyamir.maxeremin.tutorial.repository.StartStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("startStationService")
public class StartStationService {
    private StartStationRepository startStationRepository;

    @Autowired
    public StartStationService(StartStationRepository stationService) {
        this.startStationRepository = stationService;
    }

    public Iterable<StartStation> findAll() {
        return startStationRepository.findAll();
    }

    public StartStation findByName(String name) {
        return startStationRepository.findByName(name);
    }

    public void saveStartStation(StartStation startStation) {
        startStationRepository.save(startStation);
    }

    public void saveStartStations(Iterable<StartStation> startStations) {
        startStationRepository.save(startStations);
    }
}
