package com.car.rental.project.repository;

import com.car.rental.project.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

}

