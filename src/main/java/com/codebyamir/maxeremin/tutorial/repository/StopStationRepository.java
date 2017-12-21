package com.codebyamir.maxeremin.tutorial.repository;

import com.codebyamir.maxeremin.tutorial.model.StopStation;
import org.springframework.data.repository.CrudRepository;

public interface StopStationRepository extends CrudRepository<StopStation, Long> {
    StopStation findByName(String name);
}
