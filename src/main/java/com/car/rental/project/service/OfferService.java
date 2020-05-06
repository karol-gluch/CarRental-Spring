package com.car.rental.project.service;

import com.car.rental.project.model.Offer;

import java.util.List;

public interface OfferService {
    void save(Offer offer);
    List<Offer> findAll();

}
