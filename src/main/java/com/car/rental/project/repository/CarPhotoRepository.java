package com.car.rental.project.repository;

import com.car.rental.project.model.Car;
import com.car.rental.project.model.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarPhotoRepository extends JpaRepository<CarPhoto, Long> {
    @Query("SELECT CP FROM CarPhoto CP WHERE CP.car = :id")
    List<CarPhoto> findPhotoWithCarId(@Param("id") Car id);
}
