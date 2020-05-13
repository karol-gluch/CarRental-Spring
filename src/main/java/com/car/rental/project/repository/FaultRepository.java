package com.car.rental.project.repository;

import com.car.rental.project.model.Fault;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaultRepository extends JpaRepository<Fault, Long> {
}
