package com.space.repository;

import com.space.model.ShipEntity;
import com.space.model.ShipType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Date;
import java.util.List;

public interface FindShipRepository extends PagingAndSortingRepository<ShipEntity, Long> {

    List<ShipEntity> findAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContaining
            (Date afterDate, Date beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet);

    List<ShipEntity> findAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndIsUsedAndShipType
            (Date afterDate, Date beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet, Boolean isUsed, ShipType shipType);

    List<ShipEntity> findAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndIsUsed
            (Date afterDate, Date beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet, Boolean isUsed);

    List<ShipEntity> findAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndShipType
            (Date afterDate, Date beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet, ShipType shipType);

    Integer countAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContaining
            (Date prodDate, Date prodDate2, Integer crewSize, Integer crewSize2, Double speed, Double speed2, Double rating, Double rating2, String name, String planet);

    Integer countAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndIsUsedAndShipType
            (Date afterDate, Date beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet, Boolean isUsed, ShipType shipType);

    Integer countAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndIsUsed
            (Date afterDate, Date beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet, Boolean isUsed);

    Integer countAllByProdDateAfterAndProdDateBeforeAndCrewSizeLessThanAndCrewSizeGreaterThanAndSpeedLessThanAndSpeedGreaterThanAndRatingLessThanAndRatingGreaterThanAndNameIsContainingAndPlanetIsContainingAndShipType
            (Date afterDate, Date beforeDate, Integer maxSize, Integer minSize, Double maxSpeed, Double minSpeed, Double maxRating, Double minRating, String name, String planet, ShipType shipType);
}
