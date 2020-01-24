package com.space.repository;

import com.space.model.ShipEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UpdateShipRepository extends PagingAndSortingRepository<ShipEntity, Long> {

}
