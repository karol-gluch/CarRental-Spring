package com.car.rental.project.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer price;
    private String url;

    public Offer(String name, String description, Integer price, String mediaUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }

    public Offer() {};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String mediaUrl) {
        this.url = url;
    }
}
