package com.codebyamir.maxeremin.tutorial.repository;

import com.codebyamir.maxeremin.tutorial.model.StartStation;
import org.springframework.data.repository.CrudRepository;

public interface StartStationRepository extends CrudRepository<StartStation, Long> {
    StartStation findByName(String name);
}
