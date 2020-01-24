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
        if (name == null || name.length() > 50 || name.length() == 0) {
            throw new NotValidDataException("Current data: " + name + " does not belong to this date range " + 1 + "-" + 50);
        }
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        if (planet == null || planet.length() > 50 || planet.length() == 0) {
            throw new NotValidDataException("Current data: " + planet + " does not belong to this date range " + 1 + "-" + 50);
        }
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        if (shipType == null) {
            throw new NotValidDataException("Current data: isNull ");
        }
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }


    public void setProdDate(Date prodDate) {
        System.out.println(prodDate + " date from setter");
        Date dateAfter = Date.valueOf(Constant.DATE_AFTER);
        Date dateBefore = Date.valueOf(Constant.DATE_BEFORE);
        if (prodDate.after(dateAfter) && prodDate.before(dateBefore)) {
            this.prodDate = prodDate;
        } else {
            throw new NotValidDataException("Current date: " + prodDate.toString() + " does not belong to this date range " + Constant.DATE_AFTER + "-" + Constant.DATE_BEFORE);
        }
    }

    public void setProdDate(Long prodDate) {
        System.out.println(prodDate + " date from setter");
        if (prodDate == null || prodDate < 0) {
            throw new NotValidDataException("Current data: isNull ");
        }

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
        if (speed == null) {
            throw new NotValidDataException("Current speed " + speed + " isNull ");
        }
        speed = DataUtil.round(speed);
        if (speed > Constant.MIN_SHIP_SPEED && speed <= Constant.MAX_SHIP_SPEED) {
            this.speed = speed;
        } else {
            throw new NotValidDataException("Current speed " + speed + " does not belong to this number range " + Constant.MIN_SHIP_SPEED + "-" + Constant.MAX_SHIP_SPEED);
        }
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        if (crewSize == null) {
            throw new NotValidDataException("Current speed " + speed + " isNull ");
        }
        if (crewSize > Constant.MIN_CREW_SIZE && crewSize <= Constant.MAX_CREW_SIZE) {
            this.crewSize = crewSize;
        } else {
            throw new NotValidDataException("Current crewSize " + crewSize + " does not belong to this number range " + Constant.MIN_CREW_SIZE + "-" + Constant.MAX_CREW_SIZE);
        }
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        rating = DataUtil.round(rating);
        this.rating = rating;
    }


}
