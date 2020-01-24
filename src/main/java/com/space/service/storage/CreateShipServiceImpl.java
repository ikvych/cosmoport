package com.space.service.storage;

import com.space.exeption.NotValidDataException;
import com.space.model.EntityRequestDTO;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.repository.CreateShipRepository;
import com.space.service.CreateShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Calendar;


@Service
public class CreateShipServiceImpl extends AbstractShipService implements CreateShipService {

    @Autowired
    private CreateShipRepository createShipRepository;

    @Override
    @Transactional
    public EntityResponseDTO createShip(EntityRequestDTO requestDTO) {
        EntityResponseDTO responseDTO = new EntityResponseDTO();
        ShipEntity shipEntity = new ShipEntity();

        if (isNameValid(requestDTO.getName())) {
            shipEntity.setName(requestDTO.getName());
        }
        if (isPlanetValid(requestDTO.getPlanet())) {
            shipEntity.setPlanet(requestDTO.getPlanet());
        }
        if (isShipTypeValid(requestDTO.getShipType())) {
            shipEntity.setShipType(requestDTO.getShipType());
        }
        if (isProdDateValid(requestDTO.getProdDate())) {
            shipEntity.setProdDate(requestDTO.getProdDate());
        }
        if (isSpeedValid(requestDTO.getSpeed())) {
            shipEntity.setSpeed(requestDTO.getSpeed());
        }
        if (isCrewSizeValid(requestDTO.getCrewSize())) {
            shipEntity.setCrewSize(requestDTO.getCrewSize());
        }
        shipEntity.setUsed(requestDTO.isUsed);
        double rating = calcRating(shipEntity.getProdDate(), shipEntity.getSpeed(), shipEntity.getUsed());

        shipEntity.setRating(rating);
        ShipEntity createdEntity = createShipRepository.save(shipEntity);
        createResponseEntity(responseDTO, createdEntity);
        return responseDTO;
    }

}
