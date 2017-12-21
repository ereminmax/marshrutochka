package com.codebyamir.maxeremin.tutorial.service;

import com.codebyamir.maxeremin.tutorial.model.StopStation;
import com.codebyamir.maxeremin.tutorial.repository.StopStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("stopStationService")
public class StopStationService {
    private StopStationRepository stopStationRepository;

    @Autowired
    public StopStationService(StopStationRepository stationService) {
        this.stopStationRepository = stationService;
    }

    public Iterable<StopStation> findAll() {
        return stopStationRepository.findAll();
    }

    public StopStation findByName(String name) {
        return stopStationRepository.findByName(name);
    }

    public void saveStopStation(StopStation stopStation) {
        stopStationRepository.save(stopStation);
    }

    public void saveStopStations(Iterable<StopStation> stopStations) {
        stopStationRepository.save(stopStations);
    }
}
