package com.car.rental.project.model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mark;
    private String model;
    private String yearOfProduction;
    @OneToMany(mappedBy = "car")
    private Set<CarPhoto> carPhoto;

    @OneToOne(mappedBy = "car")
    private Offer offer;

    public Car(String mark, String model, String yearOfProduction) {
        this.mark = mark;
        this.model = model;
        this.yearOfProduction = yearOfProduction;
    }

    public Car(String mark, String model, String yearOfProduction, Set<CarPhoto> carPhoto) {
        this.mark = mark;
        this.model = model;
        this.yearOfProduction = yearOfProduction;
        this.carPhoto = carPhoto;
    }

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Set getCarPhoto() {
        return carPhoto;
    }

    public void setCarPhoto(Set carPhoto) {
        this.carPhoto = carPhoto;
    }
}
