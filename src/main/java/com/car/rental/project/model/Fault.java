package com.car.rental.project.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "fault")
public class Fault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeFault;
    private String titleFault;
    private String descriptionFault;

    @ManyToOne()
    @JoinColumn(name = "rent_id", referencedColumnName = "id")
    private Rent rent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeFault() {
        return typeFault;
    }

    public void setTypeFault(String typeFault) {
        this.typeFault = typeFault;
    }

    public String getTitleFault() {
        return titleFault;
    }

    public void setTitleFault(String titleFault) {
        this.titleFault = titleFault;
    }

    public String getDescriptionFault() {
        return descriptionFault;
    }

    public void setDescriptionFault(String descriptionFault) {
        this.descriptionFault = descriptionFault;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
}

