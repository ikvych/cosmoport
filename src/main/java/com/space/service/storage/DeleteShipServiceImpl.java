package com.space.service.storage;

import com.space.exeption.NotValidDataException;
import com.space.exeption.ShipNotFoundException;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.repository.DeleteShipRepository;
import com.space.service.DeleteShipService;
import com.space.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteShipServiceImpl implements DeleteShipService {

    @Autowired
    private DeleteShipRepository deleteShipRepository;

    @Override
    @Transactional
    public boolean deleteById(String requestId) {
        long id = DataUtil.parseId(requestId);
        if (deleteShipRepository.findById(id).isPresent()) {
            deleteShipRepository.deleteById(id);
        }
        else {
            throw new ShipNotFoundException("There is no ship with Id - " + id);
        }
        return true;
    }
}
