package com.space.repository;

import com.space.model.ShipEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeleteShipRepository extends PagingAndSortingRepository<ShipEntity, Long> {

}
