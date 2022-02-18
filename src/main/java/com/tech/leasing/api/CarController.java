package com.tech.leasing.api;


import com.tech.leasing.dto.CarDTO;
import com.tech.leasing.service.CarServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/car")
@Slf4j
public class CarController {
    private final CarServiceImplementation carService;

    public CarController(CarServiceImplementation carService) {
        this.carService = carService;
    }

    @PostMapping("/createCar")
    public ResponseEntity<Object> createCar(@RequestBody CarDTO carDTO, Principal principal) {
        log.info("New car with id {}", carDTO.getCarId());
        return new ResponseEntity<>(carService.createCar(carDTO, principal), HttpStatus.CREATED);
    }
    @PutMapping("/updateCar")
    public ResponseEntity<Object> updateCar(@RequestBody CarDTO carDTO, Principal principal) {
        log.info("updating car with id {}", carDTO.getCarId());
        return new ResponseEntity<>(carService.updateCar(carDTO, principal), HttpStatus.OK);
    }
    @DeleteMapping("/deleteCar")
    public ResponseEntity deleteCar(@RequestBody CarDTO carDTO, Principal principal) {
        log.info("Deleting car with id {}", carDTO.getCarId());
        carService.deleteCar(carDTO, principal);
        return new ResponseEntity<>("Deleted!", HttpStatus.OK);
    }
    @GetMapping("/getInformation")
    public ResponseEntity<Object> getInformationAboutCar(@RequestBody CarDTO carDTO) {
        log.info("getting information about car with id {}", carDTO.getCarId());
        return new ResponseEntity<>(carService.getInformationAboutCar(carDTO), HttpStatus.OK);
    }
}
