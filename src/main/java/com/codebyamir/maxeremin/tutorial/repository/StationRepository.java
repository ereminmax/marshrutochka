package com.codebyamir.maxeremin.tutorial.repository;

import com.codebyamir.maxeremin.tutorial.model.StartStation;
import com.codebyamir.maxeremin.tutorial.model.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<Station, Long> {
    Station findByName(String name);
}
