package com.space.service.storage;

import com.space.exeption.ShipNotFoundException;
import com.space.model.EntityRequestDTO;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.repository.UpdateShipRepository;
import com.space.service.UpdateShipService;
import com.space.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateShipServiceImpl extends AbstractShipService implements UpdateShipService {

    @Autowired
    private UpdateShipRepository updateShipRepository;

    @Override
    @Transactional
    public EntityResponseDTO updateShipById(String requestId, EntityRequestDTO requestDTO) {
        EntityResponseDTO responseDTO = new EntityResponseDTO();
        long id = DataUtil.parseId(requestId);
        if (updateShipRepository.findById(id).isPresent()) {
            ShipEntity shipEntity = updateShipRepository.findById(id).get();
            if (isNotNull(requestDTO.getName()) && isNameValid(requestDTO.getName())) {
                shipEntity.setName(requestDTO.getName());
            }
            if (isNotNull(requestDTO.getPlanet()) && isPlanetValid(requestDTO.getPlanet())) {
                shipEntity.setPlanet(requestDTO.getPlanet());
            }
            if (isNotNull(requestDTO.getShipType()) && isShipTypeValid(requestDTO.getShipType())) {
                shipEntity.setShipType(requestDTO.getShipType());
            }
            if (isNotNull(requestDTO.getProdDate()) && isProdDateValid(requestDTO.getProdDate())) {
                shipEntity.setProdDate(requestDTO.getProdDate());
            }
            if (isNotNull(requestDTO.getSpeed()) && isSpeedValid(requestDTO.getSpeed())) {
                shipEntity.setSpeed(requestDTO.getSpeed());
            }
            if (isNotNull(requestDTO.getCrewSize()) && isCrewSizeValid(requestDTO.getCrewSize())) {
                shipEntity.setCrewSize(requestDTO.getCrewSize());
            }
            if (isRatingNeedToChange(requestDTO.getProdDate(), requestDTO.getSpeed(), requestDTO.getUsed())) {
                double rating = calcRating(shipEntity.getProdDate(), shipEntity.getSpeed(), shipEntity.getUsed());
                shipEntity.setRating(rating);
            }

            updateShipRepository.save(shipEntity);
            createResponseEntity(responseDTO, shipEntity);
        }
        else {
            throw new ShipNotFoundException("There is no ship with Id - " + id);
        }
        return responseDTO;
    }


}
