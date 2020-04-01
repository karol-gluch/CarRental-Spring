package com.car.rental.project.service;

import com.car.rental.project.model.Car;
import com.car.rental.project.model.CarPhoto;
import com.car.rental.project.model.Offer;
import com.car.rental.project.model.OfferWithCar;
import com.car.rental.project.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public void save(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    @Override
    public List<OfferWithCar> findAllOffersWithCars() {
        List<Offer> offers = offerRepository.findAll();
        List<OfferWithCar> offersWithCars = new ArrayList<>();
        offers.forEach(o ->{
            OfferWithCar x = new OfferWithCar();
            x.setId(o.getCar().getId());
            x.setCarPhoto(o.getCar().getCarPhoto());
            x.setMark(o.getCar().getMark());
            x.setModel(o.getCar().getModel());
            x.setYearOfProduction(o.getCar().getYearOfProduction());
            x.setDescription(o.getDescription());
            x.setPrice(o.getPrice());
            offersWithCars.add(x);
        });
        return offersWithCars;
    }



}