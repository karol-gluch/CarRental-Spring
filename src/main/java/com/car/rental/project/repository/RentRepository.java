package com.car.rental.project.repository;

import com.car.rental.project.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {
    @Query(value = "SELECT * FROM Rent r inner join rent_offers o on o.rent_id = r.id where o.offer_id = ?1", nativeQuery = true)
    List<Rent> find(Long id);

    @Query(value = "SELECT * FROM Rent r inner join rent_offers o on o.rent_id = r.id where o.offer_id = ?1", nativeQuery = true)
    Rent findR(Long id);

}
