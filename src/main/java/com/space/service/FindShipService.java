package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.model.ShipType;

import java.sql.Date;
import java.util.List;

public interface FindShipService {

    public List<ShipEntity> findAllById(Long id);

    public List<ShipEntity> findAllByProdDateAndCrewSize(Date dateAfter, Date dateBefore, Integer maxCrewSize, Integer minCrewSize);

    public List<ShipEntity> nameIsContaining(String name);

    public List<ShipEntity> findAnything (Long afterDate, Long beforeDate,
                                          Integer maxSize, Integer minSize,
                                          Double maxSpeed, Double minSpeed,
                                          Double maxRating, Double minRating,
                                          String name, String planet,
                                          Boolean isUsed, ShipType shipType,
                                          ShipOrder shipOrder, Integer pageNumber,
                                          Integer pageSize);

    public Integer countAll (Long afterDate, Long beforeDate,
                             Integer maxSize, Integer minSize,
                             Double maxSpeed, Double minSpeed,
                             Double maxRating, Double minRating,
                             String name, String planet,
                             Boolean isUsed, ShipType shipType);

    public EntityResponseDTO findById(String id);
}
