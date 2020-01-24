package com.space.model;

import com.space.constans.Constant;
import com.space.exeption.NotValidDataException;
import com.space.util.DataUtil;

import javax.annotation.Generated;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

@Entity
@Table(name = "ship")
public class ShipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "planet", length = 50)
    private String planet;

    @Column(name = "shipType", length = 9)
    @Convert(converter = ShipType.PersistJPAConverter.class)
    private ShipType shipType;

    @Column(name = "prodDate")
    private Date prodDate;

    @Column(name = "isUsed")
    private Boolean isUsed;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "crewSize")
    private Integer crewSize;

    @Column(name = "rating")
    private Double rating;

    public ShipEntity() {
        super();
    }

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

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setProdDate(Long prodDate) {
        setProdDate(new Date(prodDate));
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        if (used == null) {
            this.isUsed = false;
        }
        else {
            isUsed = used;
        }
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        rating = DataUtil.round(rating);
        this.rating = rating;
    }


}
