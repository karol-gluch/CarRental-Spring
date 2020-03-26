package com.car.rental.project.repository;

import com.car.rental.project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(value = "SELECT c FROM Car c left join c.offer o WHERE o.id IS NULL")
    List<Car> find();
}
