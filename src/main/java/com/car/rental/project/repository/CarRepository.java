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

    @Query(value = "SELECT * FROM CAR C JOIN OFFER O ON C.ID = O.CAR_ID AND CASE WHEN ?1 IS NULL THEN O.PRICE >= 0 ELSE O.PRICE >= ?1 end AND CASE WHEN ?2 IS NULL THEN O.PRICE <=2000 ELSE O.PRICE <=?2 end AND CASE WHEN ?3 IS NULL THEN C.FUEL_TYPE = 'Benzyna' OR C.FUEL_TYPE = 'Ropa' ELSE C.FUEL_TYPE = ?3 end AND CASE WHEN ?4 IS NULL THEN C.BODY_TYPE = 'Sedan' OR C.BODY_TYPE = 'Kombi' OR C.BODY_TYPE = 'Hatchback' ELSE C.BODY_TYPE = ?4 end AND CASE WHEN ?5 IS NULL THEN C.YEAR_OF_PRODUCTION >= 1900 ELSE C.YEAR_OF_PRODUCTION >= ?5 end AND CASE WHEN ?6 IS NULL THEN C.YEAR_OF_PRODUCTION <= 2100 ELSE C.YEAR_OF_PRODUCTION <= ?6 end AND CASE WHEN ?7 IS NULL THEN C.ENGINE_CAPACITY >= 1.0 ELSE C.ENGINE_CAPACITY >= ?7 end AND CASE WHEN ?8 IS NULL THEN  C.ENGINE_CAPACITY <= 7.0 ELSE C.ENGINE_CAPACITY <= ?8 end AND CASE WHEN ?9 IS NULL THEN C.NUMBER_OF_PLACES >=1 ELSE C.NUMBER_OF_PLACES >= ?9 end AND CASE WHEN ?10 IS NULL THEN C.NUMBER_OF_PLACES <= 8 ELSE C.NUMBER_OF_PLACES <= ?10 end", nativeQuery = true)
        List <Car> findOf(String cenaOd, String cenaDo, String rodzajPaliwa, String typNadwozia, String rokOd, String rokDo, String pojemnoscOd, String pojemnoscDo, String liczbaOd, String liczbaDo);

}
