package com.car.rental.project.repository;

import com.car.rental.project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(value = "SELECT c FROM Car c left join c.offer o WHERE o.id IS NULL")
    List<Car> find();

    @Query(value = "SELECT * FROM CAR C JOIN OFFER O ON C.ID = O.CAR_ID WHERE O.PRICE >= ?1 AND O.PRICE <= ?2 AND C.FUEL_TYPE = ?3 AND C.BODY_TYPE = ?4 AND C.YEAR_OF_PRODUCTION >= ?5 AND C.YEAR_OF_PRODUCTION <= ?6 AND C.ENGINE_CAPACITY >= ?7 AND C.ENGINE_CAPACITY <= ?8 AND C.NUMBER_OF_PLACES >= ?9 AND C.NUMBER_OF_PLACES <= ?10", nativeQuery = true)
    List <Car> findO(String cenaOd, String cenaDo, String rodzajPaliwa, String typNadwozia, String rokOd, String rokDo, Double pojemnoscOd, Double pojemnoscDo, String liczbaOd, String liczbaDo);

}
