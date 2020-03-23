package com.car.rental.project.repository;

import com.car.rental.project.model.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPhotoRepository extends JpaRepository<CarPhoto, Long> {
}
