package com.space.repository;

import com.space.model.ShipEntity;
import com.space.model.ShipType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Date;
import java.util.List;

public interface CreateShipRepository extends PagingAndSortingRepository<ShipEntity, Long> {

}
