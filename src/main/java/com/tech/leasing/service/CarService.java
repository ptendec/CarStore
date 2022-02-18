package com.tech.leasing.service;

import com.tech.leasing.dto.CarDTO;
import com.tech.leasing.exceptions.ResourceNotFoundException;
import com.tech.leasing.model.Car;
import org.springframework.security.core.parameters.P;

import java.security.Principal;

public interface CarService {
    Car createCar(CarDTO carDTO, Principal principal);
    Car updateCar(CarDTO carDTO, Principal principal);
    void deleteCar(CarDTO carDTO,Principal principal);
    Car getInformationAboutCar(CarDTO carDTO);
}
