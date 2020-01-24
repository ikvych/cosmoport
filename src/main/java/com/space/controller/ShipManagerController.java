package com.space.controller;

import com.space.exeption.NotValidDataException;
import com.space.model.EntityRequestDTO;
import com.space.model.EntityResponseDTO;
import com.space.model.ShipEntity;
import com.space.model.ShipType;
import com.space.repository.UpdateShipRepository;
import com.space.service.CreateShipService;
import com.space.service.DeleteShipService;
import com.space.service.FindShipService;
import com.space.service.UpdateShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
        List<ShipEntity> ships = findShipService.findAnything(after, before, maxCrewSize, minCrewSize, maxSpeed, minSpeed, maxRating, minRating, name, planet, isUsed, shipType, order, pageNumber, pageSize);
        for (ShipEntity ship : ships) {
            System.out.println(ship.getName());
            System.out.println(ship.getProdDate());
            System.out.println(ship.getCrewSize());
        }
        return ships;
    }

    @GetMapping("ships/count")
    public Integer getShipsCount(@RequestParam(required = false, name = "name") String name, @RequestParam(required = false, name = "planet") String planet,
                                     @RequestParam(required = false, name = "shipType") ShipType shipType, @RequestParam(required = false, name = "after") Long after,
                                     @RequestParam(required = false, name = "before") Long before, @RequestParam(required = false, name = "isUsed") Boolean isUsed,
                                     @RequestParam(required = false, name = "minSpeed") Double minSpeed, @RequestParam(required = false, name = "maxSpeed") Double maxSpeed,
                                     @RequestParam(required = false, name = "minCrewSize") Integer minCrewSize, @RequestParam(required = false, name = "maxCrewSize") Integer maxCrewSize,
                                     @RequestParam(required = false, name = "minRating") Double minRating, @RequestParam(required = false, name = "maxRating") Double maxRating) {
        return findShipService.countAll(after, before, maxCrewSize, minCrewSize, maxSpeed, minSpeed, maxRating, minRating, name, planet, isUsed, shipType);
    }

    @PostMapping("ships")
    public ResponseEntity<EntityResponseDTO> createShip(@RequestBody EntityRequestDTO requestDTO) {
        ResponseEntity<EntityResponseDTO> responseEntity = createShipService.createShip(requestDTO);
        return responseEntity;
    }

    @GetMapping("ships/{id}")
    public ResponseEntity<EntityResponseDTO> getShip(@PathVariable("id") String id) {
        EntityResponseDTO responseEntity;
        try {
            responseEntity = findShipService.findById(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotValidDataException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @DeleteMapping("ships/{id}")
    public ResponseEntity<HttpStatus> deleteShip(@PathVariable("id") String id) {
        boolean response;
        try {
            response = deleteShipService.deleteById(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotValidDataException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("ships/{id}")
    public ResponseEntity<EntityResponseDTO> upDateShips(@PathVariable("id") String id, @RequestBody EntityRequestDTO requestDTO) {
        EntityResponseDTO responseEntity;
        try {
            responseEntity = updateShipService.updateShipById(id, requestDTO);
        } catch (NotValidDataException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }


}
