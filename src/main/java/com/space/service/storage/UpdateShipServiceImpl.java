package com.space.service.storage;

import com.space.exeption.NotValidDataException;
import com.space.model.EntityRequestDTO;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.repository.UpdateShipRepository;
import com.space.service.UpdateShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Calendar;

@Service
public class UpdateShipServiceImpl implements UpdateShipService {

    @Autowired
    private UpdateShipRepository updateShipRepository;

    @Override
    @Transactional
    public EntityResponseDTO updateShipById(String requestId, EntityRequestDTO requestDTO) {
        long id = Integer.parseInt(requestId);
        if (id < 1) {
            throw new NumberFormatException("Current data: isNull ");
        }
        EntityResponseDTO responseDTO = new EntityResponseDTO();
        if (updateShipRepository.findById(id).isPresent()) {
            ShipEntity shipEntity = updateShipRepository.findById(id).get();
            if (requestDTO.name != null) {
                shipEntity.setName(requestDTO.name);
            }
            if (requestDTO.planet != null) {
                shipEntity.setPlanet(requestDTO.planet);
            }
            if (requestDTO.shipType != null) {
                shipEntity.setShipType(requestDTO.shipType);
            }
            if (requestDTO.prodDate != null) {
                shipEntity.setProdDate(requestDTO.prodDate);
            }
            if (requestDTO.isUsed != null) {
                shipEntity.setUsed(requestDTO.isUsed);
            }
            if (requestDTO.speed != null) {
                shipEntity.setSpeed(requestDTO.speed);
            }
            if (requestDTO.crewSize != null) {
                shipEntity.setCrewSize(requestDTO.crewSize);
            }
            if (requestDTO.speed != null || requestDTO.isUsed != null || requestDTO.prodDate != null) {
                double rating;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(shipEntity.getProdDate());
                if (shipEntity.getUsed()) {
                    rating = (80 * shipEntity.getSpeed() * 0.5) / (3019 - calendar.get(Calendar.YEAR) + 1);
                    System.out.println(shipEntity.getProdDate() + " this is year");
                } else {
                    rating = (80 * shipEntity.getSpeed() * 1) / (3019 - calendar.get(Calendar.YEAR) + 1);
                }
                shipEntity.setRating(rating);
            }
            updateShipRepository.save(shipEntity);
            createResponseEntity(responseDTO, shipEntity);
        }
        else {
            throw new IllegalArgumentException("Current data: does't exist ");
        }
        return responseDTO;
    }

    private void createResponseEntity(final EntityResponseDTO responseDTO, final ShipEntity shipEntity) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
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
        });
    }
}
