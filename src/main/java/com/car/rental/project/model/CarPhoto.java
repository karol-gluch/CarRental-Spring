package com.car.rental.project.model;

import javax.persistence.*;
import java.util.Base64;

@Entity
@Table(name = "car_photo")
public class CarPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] photo;
    @ManyToOne()
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    public CarPhoto(byte[] photo, Car car) {
        this.photo = photo;
        this.car = car;
    }

    public CarPhoto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getPhoto1(){
        return Base64.getEncoder().encodeToString(photo);
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
