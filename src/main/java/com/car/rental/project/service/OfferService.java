package com.car.rental.project.service;

import com.car.rental.project.model.Offer;
import com.car.rental.project.model.OfferWithCar;

import java.util.List;

public interface OfferService {
    void save(Offer offer);
    List<Offer> findAll();
    List<OfferWithCar> findAllOffersWithCars();
}
