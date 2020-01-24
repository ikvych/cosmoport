package com.space.service;

import com.space.model.EntityRequestDTO;
import com.space.model.EntityResponseDTO;
import org.springframework.http.ResponseEntity;

public interface CreateShipService {

    EntityResponseDTO createShip(EntityRequestDTO requestDTO);
}
