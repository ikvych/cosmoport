package com.space.service.storage;

import com.space.constans.Constant;
import com.space.controller.ShipOrder;
import com.space.exeption.NotValidDataException;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.model.ShipType;
import com.space.repository.FindShipRepository;
import com.space.service.FindShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindShipServiceImpl implements FindShipService {
    private Date afterDate;
    private Date beforeDate;
    private Integer maxSize;
    private Integer minSize;
    private Double maxSpeed;
    private Double minSpeed;
    private Double maxRating;
    private Double minRating;
    private String name;
    private String planet;
    private Boolean isUsed;
    private ShipType shipType;

    @Autowired
    private FindShipRepository findShipRepository;

    @Override
    public List<ShipEntity> findAllById(Long id) {
        return findShipRepository.findAllById(id);
    }

    @Override
    public List<ShipEntity> findAllByProdDateAndCrewSize(Date dateAfter, Date dateBefore, Integer maxCrewSize, Integer minCrewSize) {
        return findShipRepository.findAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThan(dateAfter, dateBefore, maxCrewSize, minCrewSize);
    }

    @Override
    public List<ShipEntity> nameIsContaining(String name) {
        return findShipRepository.findAllByNameIsContaining(name);
    }

    @Override
    public List<ShipEntity> findAnything(Long afterDate, Long beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet, Boolean isUsed, ShipType shipType, ShipOrder shipOrder, Integer pageNumber, Integer pageSize) {
        init(afterDate, beforeDate, maxSize, minSize, maxSpeed, minSpeed, maxRating, minRating, name, planet, isUsed, shipType);
        List<ShipEntity> ships = findAllShips();
        sortAllShips(ships, shipOrder);
        return selectPage(ships, pageNumber, pageSize);
    }

    @Override
    public Integer countAll(Long afterDate, Long beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet, Boolean isUsed, ShipType shipType) {
        init(afterDate, beforeDate, maxSize, minSize, maxSpeed, minSpeed, maxRating, minRating, name, planet, isUsed, shipType);
        return countAllShips();
    }

    @Override
    @Transactional
    public EntityResponseDTO findById(String requestId) {
        long id = Integer.parseInt(requestId);
        if (id < 1) {
            throw new NumberFormatException("Current data: isNull ");
        }
        EntityResponseDTO responseDTO = new EntityResponseDTO();
        if (findShipRepository.findById(id).isPresent()) {
            ShipEntity shipEntity = findShipRepository.findById(id).get();
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
        else {
            throw new NotValidDataException("Current data: does't exist ");
        }
        return responseDTO;
    }

    private List<ShipEntity> selectPage(List<ShipEntity> ships, Integer pageNumber, Integer pageSize) {
        int numberOfPage;
        if (pageNumber == null) {
            numberOfPage = 0;
        }
        else {
            numberOfPage = pageNumber;
        }
        int sizeOfPage;
        if (pageSize == null) {
            sizeOfPage = 3;
        }
        else {
            sizeOfPage = pageSize;
        }
        int skip = numberOfPage * sizeOfPage;
        List<ShipEntity> result = new ArrayList<>();
        for (int i = skip; i < Math.min(skip + sizeOfPage, ships.size()); i++) {
            result.add(ships.get(i));
        }
        return result;
    }

    private void sortAllShips(List<ShipEntity> ships, ShipOrder order) {
        if (order == ShipOrder.ID) {
            ships.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
        } /*else if (order == ShipOrder.DATE) {
            ships.sort((o1, o2) -> (int) (o1.getProdDate() - o2.getProdDate()));
        } */else if (order == ShipOrder.SPEED) {
            ships.sort((o1, o2) -> {
                if (o1.getSpeed() > o2.getSpeed())
                    return 1;
                else if (o1.getSpeed().equals(o2.getSpeed()))
                    return 0;
                else
                    return -1;
            });
        } else if (order == ShipOrder.RATING) {
            ships.sort((o1, o2) -> {
                if (o1.getRating() > o2.getRating())
                    return 1;
                else if (o1.getRating().equals(o2.getRating()))
                    return 0;
                else
                    return -1;
            });
        }
    }

    private Integer countAllShips() {
        if (this.isUsed == null && this.shipType == null) {
            return findShipRepository.countAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContaining
                    (this.afterDate, this.beforeDate, this.maxSize, this.minSize, this.maxSpeed, this.minSpeed, this.maxRating, this.minRating, this.name, this.planet);
        }
        if (this.isUsed != null && this.shipType != null) {
            return findShipRepository.countAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndIsUsedAndShipType
                    (this.afterDate, this.beforeDate, this.maxSize, this.minSize, this.maxSpeed, this.minSpeed, this.maxRating, this.minRating, this.name, this.planet, this.isUsed, this.shipType);
        }
        if (this.isUsed == null) {
            return findShipRepository.countAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndShipType
                    (this.afterDate, this.beforeDate, this.maxSize, this.minSize, this.maxSpeed, this.minSpeed, this.maxRating, this.minRating, this.name, this.planet, this.shipType);
        }
        return findShipRepository.countAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndIsUsed
                (this.afterDate, this.beforeDate, this.maxSize, this.minSize, this.maxSpeed, this.minSpeed, this.maxRating, this.minRating, this.name, this.planet, this.isUsed);
    }


    private List<ShipEntity> findAllShips() {
        if (this.isUsed == null && this.shipType == null) {
            return findShipRepository.findAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContaining
                    (this.afterDate, this.beforeDate, this.maxSize, this.minSize, this.maxSpeed, this.minSpeed, this.maxRating, this.minRating, this.name, this.planet);
        }
        if (this.isUsed != null && this.shipType != null) {
            return findShipRepository.findAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndIsUsedAndShipType
                    (this.afterDate, this.beforeDate, this.maxSize, this.minSize, this.maxSpeed, this.minSpeed, this.maxRating, this.minRating, this.name, this.planet, this.isUsed, this.shipType);
        }
        if (this.isUsed == null) {
            return findShipRepository.findAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndShipType
                    (this.afterDate, this.beforeDate, this.maxSize, this.minSize, this.maxSpeed, this.minSpeed, this.maxRating, this.minRating, this.name, this.planet, this.shipType);
        }
        return findShipRepository.findAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndIsUsed
                (this.afterDate, this.beforeDate, this.maxSize, this.minSize, this.maxSpeed, this.minSpeed, this.maxRating, this.minRating, this.name, this.planet, this.isUsed);
    }

    private void init(Long afterDate, Long beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet, Boolean isUsed, ShipType shipType) {
        if (afterDate == null) {
            this.afterDate = Date.valueOf(Constant.DATE_AFTER);
        } else {
            this.afterDate = new Date(afterDate);
        }
        if (beforeDate == null) {
            this.beforeDate = Date.valueOf(Constant.DATE_BEFORE);
        } else {
            this.beforeDate = new Date(beforeDate);
        }
        if (maxSize == null) {
            this.maxSize = Constant.MAX_CREW_SIZE;
        } else {
            this.maxSize = maxSize;
        }
        if (minSize == null) {
            this.minSize = Constant.MIN_CREW_SIZE;
        } else {
            this.minSize = minSize;
        }
        if (maxSpeed == null) {
            this.maxSpeed = Constant.MAX_SHIP_SPEED;
        } else {
            this.maxSpeed = maxSpeed;
        }
        if (minSpeed == null) {
            this.minSpeed = Constant.MIN_SHIP_SPEED;
        } else {
            this.minSpeed = minSpeed;
        }
        if (maxRating == null) {
            this.maxRating = 50.0;
        } else {
            this.maxRating = maxRating;
        }
        if (minRating == null) {
            this.minRating = 0.0;
        } else {
            this.minRating = minRating;
        }
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }
        if (planet == null) {
            this.planet = "";
        } else {
            this.planet = planet;
        }
        if (isUsed == null) {
            this.isUsed = null;
        } else {
            this.isUsed = isUsed;
        }
        if (shipType == null) {
            this.shipType = null;
        } else {
            this.shipType = shipType;
        }
    }
}
