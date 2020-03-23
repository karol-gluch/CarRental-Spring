package com.car.rental.project.service;

import com.car.rental.project.model.Offer;
import com.car.rental.project.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public void save(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }


}
