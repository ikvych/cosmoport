package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.EntityRequestDTO;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.model.ShipType;

import java.sql.Date;
import java.util.List;

public interface UpdateShipService {
    EntityResponseDTO updateShipById(String requestId, EntityRequestDTO requestDTO);
}
