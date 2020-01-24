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
public class CreateShipServiceImpl implements CreateShipService {

    @Autowired
    private CreateShipRepository createShipRepository;

    @Override
    @Transactional
    public ResponseEntity<EntityResponseDTO> createShip(EntityRequestDTO requestDTO) {
        EntityResponseDTO responseDTO = new EntityResponseDTO();
        ShipEntity shipEntity = new ShipEntity();
        try {
            shipEntity.setName(requestDTO.name);
            shipEntity.setPlanet(requestDTO.planet);
            shipEntity.setSpeed(requestDTO.speed);
            shipEntity.setShipType(requestDTO.shipType);
            shipEntity.setUsed(requestDTO.isUsed);
            shipEntity.setProdDate(requestDTO.prodDate);
            shipEntity.setCrewSize(requestDTO.crewSize);
        } catch (NotValidDataException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        double rating;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(shipEntity.getProdDate());
        if (shipEntity.getUsed()) {
            rating = (80 * shipEntity.getSpeed() * 0.5) / (3019 - calendar.get(Calendar.YEAR) + 1);
            System.out.println(shipEntity.getProdDate() + " this is year");
        }
        else {
            rating = (80 * shipEntity.getSpeed() * 1) / (3019 - calendar.get(Calendar.YEAR) + 1);
        }
        shipEntity.setRating(rating);
        ShipEntity createdEntity = createShipRepository.save(shipEntity);
        System.out.println(createShipRepository.findById(41L).get().getName() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        createResponseEntity(responseDTO, createdEntity);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    private void createResponseEntity(final EntityResponseDTO responseDTO, final ShipEntity shipEntity) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                responseDTO.setId(shipEntity.getId());
                responseDTO.setName(shipEntity.getName());
                responseDTO.setPlanet(shipEntity.getPlanet());
                responseDTO.setCrewSize(shipEntity.getCrewSize());
                responseDTO.setProdDate(shipEntity.getProdDate().getTime());
                responseDTO.setRating(shipEntity.getRating());
                responseDTO.setShipType(shipEntity.getShipType());
                responseDTO.setUsed(shipEntity.getUsed());
                responseDTO.setSpeed(shipEntity.getSpeed());
            }
        });
    }
}
