package com.space.controller;

import com.space.exeption.NotValidDataException;
import com.space.exeption.ShipNotFoundException;
import com.space.model.EntityRequestDTO;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.model.ShipType;
import com.space.service.CreateShipService;
import com.space.service.DeleteShipService;
import com.space.service.FindShipService;
import com.space.service.UpdateShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest")
public class ShipManagerController {

    @Autowired
    private FindShipService findShipService;

    @Autowired
    private CreateShipService createShipService;

    @Autowired
    private DeleteShipService deleteShipService;

    @Autowired
    private UpdateShipService updateShipService;

    @GetMapping("ships")
    public List<ShipEntity> getShips(@RequestParam(required = false, name = "name") String name, @RequestParam(required = false, name = "planet") String planet,
                                                      @RequestParam(required = false, name = "shipType") ShipType shipType, @RequestParam(required = false, name = "after") Long after,
                                                      @RequestParam(required = false, name = "before") Long before, @RequestParam(required = false, name = "isUsed") Boolean isUsed,
                                                      @RequestParam(required = false, name = "minSpeed") Double minSpeed, @RequestParam(required = false, name = "maxSpeed") Double maxSpeed,
                                                      @RequestParam(required = false, name = "minCrewSize") Integer minCrewSize, @RequestParam(required = false, name = "maxCrewSize") Integer maxCrewSize,
                                                      @RequestParam(required = false, name = "minRating") Double minRating, @RequestParam(required = false, name = "maxRating") Double maxRating,
                                                      @RequestParam(required = false, name = "order") ShipOrder order, @RequestParam(required = false, name = "pageNumber") Integer pageNumber,
                                                      @RequestParam(required = false, name = "pageSize") Integer pageSize) {
        return findShipService.findShips(after, before, maxCrewSize, minCrewSize, maxSpeed, minSpeed, maxRating, minRating, name, planet, isUsed, shipType, order, pageNumber, pageSize);
    }

    @GetMapping("ships/count")
    public Integer getShipsCount(@RequestParam(required = false, name = "name") String name, @RequestParam(required = false, name = "planet") String planet,
                                     @RequestParam(required = false, name = "shipType") ShipType shipType, @RequestParam(required = false, name = "after") Long after,
                                     @RequestParam(required = false, name = "before") Long before, @RequestParam(required = false, name = "isUsed") Boolean isUsed,
                                     @RequestParam(required = false, name = "minSpeed") Double minSpeed, @RequestParam(required = false, name = "maxSpeed") Double maxSpeed,
                                     @RequestParam(required = false, name = "minCrewSize") Integer minCrewSize, @RequestParam(required = false, name = "maxCrewSize") Integer maxCrewSize,
                                     @RequestParam(required = false, name = "minRating") Double minRating, @RequestParam(required = false, name = "maxRating") Double maxRating) {
        return findShipService.countShips(after, before, maxCrewSize, minCrewSize, maxSpeed, minSpeed, maxRating, minRating, name, planet, isUsed, shipType);
    }

    @PostMapping("ships")
    public ResponseEntity<EntityResponseDTO> createShip(@RequestBody EntityRequestDTO requestDTO) {
        EntityResponseDTO responseEntity;
        try {
            responseEntity = createShipService.createShip(requestDTO);
        } catch (NullPointerException | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @GetMapping("ships/{id}")
    public ResponseEntity<EntityResponseDTO> getShip(@PathVariable("id") String id) {
        EntityResponseDTO responseEntity;
        try {
            responseEntity = findShipService.findShipById(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotValidDataException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @DeleteMapping("ships/{id}")
    public ResponseEntity<HttpStatus> deleteShip(@PathVariable("id") String id) {
        try {
            deleteShipService.deleteById(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ShipNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("ships/{id}")
    public ResponseEntity<EntityResponseDTO> updateShips(@PathVariable("id") String id, @RequestBody EntityRequestDTO requestDTO) {
        EntityResponseDTO responseEntity;
        try {
            responseEntity = updateShipService.updateShipById(id, requestDTO);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ShipNotFoundException r) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }


}
