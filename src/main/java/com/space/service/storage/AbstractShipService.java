package com.space.service.storage;

import com.space.constans.Constant;
import com.space.exeption.NotValidDataException;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.model.ShipType;
import com.space.util.DataUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Date;
import java.util.Calendar;

public abstract class AbstractShipService {

    void createResponseEntity(final EntityResponseDTO responseDTO, final ShipEntity shipEntity) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                responseDTO.setId(shipEntity.getId());
                responseDTO.setName(shipEntity.getName());
                responseDTO.setPlanet(shipEntity.getPlanet());
                responseDTO.setSpeed(shipEntity.getSpeed());
                responseDTO.setUsed(shipEntity.getUsed());
                responseDTO.setShipType(shipEntity.getShipType());
                responseDTO.setRating(shipEntity.getRating());
                responseDTO.setCrewSize(shipEntity.getCrewSize());
                responseDTO.setProdDate(shipEntity.getProdDate().getTime());
            }
        });
    }

    double calcRating(Date prodDate, double speed, boolean isUsed) {
        double rating;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prodDate);
        if (isUsed) {
            rating = (80 * speed * 0.5) / (3019 - calendar.get(Calendar.YEAR) + 1);
        } else {
            rating = (80 * speed * 1) / (3019 - calendar.get(Calendar.YEAR) + 1);
        }
        return rating;
    }

    boolean isNameValid(String name) {
        if (name == null || name.length() > 50 || name.length() == 0) {
            throw new IllegalArgumentException("Not valid name");
        }
        else {
            return true;
        }
    }

    boolean isPlanetValid(String planet) {
        if (planet == null || planet.length() > 50 || planet.length() == 0) {
            throw new IllegalArgumentException("Not valid planet");
        }
        else {
            return true;
        }
    }

    boolean isShipTypeValid(ShipType shipType) {
        if (shipType == null) {
            throw new IllegalArgumentException("ShipType is null");
        }
        else {
            return true;
        }
    }

    boolean isProdDateValid(Date prodDate) {
        Date dateAfter = Date.valueOf(Constant.DATE_AFTER);
        Date dateBefore = Date.valueOf(Constant.DATE_BEFORE);
        if (prodDate != null && prodDate.after(dateAfter) && prodDate.before(dateBefore)) {
            return true;
        } else {
            throw new IllegalArgumentException("Not valid date");
        }
    }

    boolean isProdDateValid(Long prodDate) {
        if (prodDate == null || prodDate < 0) {
            throw new IllegalArgumentException("Current date is null or equals 0");
        }
        return isProdDateValid(new Date(prodDate));
    }

    boolean isSpeedValid(Double speed) {
        if (speed == null) {
            throw new NullPointerException("Speed is null");
        }
        speed = DataUtil.round(speed);
        if (speed > Constant.MIN_SHIP_SPEED && speed <= Constant.MAX_SHIP_SPEED) {
            return true;
        } else {
            throw new IllegalArgumentException("Not valid speed - " + speed);
        }
    }

    boolean isCrewSizeValid(Integer crewSize) {
        if (crewSize == null) {
            throw new NullPointerException("CrewSize is null");
        }
        if (crewSize > Constant.MIN_CREW_SIZE && crewSize <= Constant.MAX_CREW_SIZE) {
            return true;
        } else {
            throw new IllegalArgumentException("Not valid crewSize - " + crewSize);
        }
    }

    boolean isRatingNeedToChange(Long prodDate, Double speed, Boolean isUsed) {
        return speed != null || isUsed != null || prodDate != null;
    }

    boolean isNotNull (Object object) {
        return object != null;
    }

}
