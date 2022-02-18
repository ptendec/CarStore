package com.tech.leasing.service;

import com.tech.leasing.dto.CarDTO;
import com.tech.leasing.exceptions.AccessDeniedException;
import com.tech.leasing.exceptions.CarNotFoundException;
import com.tech.leasing.exceptions.ResourceNotFoundException;
import com.tech.leasing.model.Car;

import com.tech.leasing.model.User;
import com.tech.leasing.repository.CarRepository;
import com.tech.leasing.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class CarServiceImplementation implements CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Car createCar(CarDTO carDTO, Principal principal) {
        Car car = new Car();
        User owner = getUserFromPrincipal(principal);
        car.setDescription(carDTO.getDescription());
        car.setMark(carDTO.getMark());
        car.setMileage(carDTO.getMileage());
        car.setManYear(carDTO.getMan_year());
        car.setUpdatedDate(LocalDateTime.now());
        car.setOwnerId(owner.getId());
        log.info("Creating car with owner phone number: {}", owner.getPhone());
        return carRepository.save(car);
    }

    private User getUserFromPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.getUserByPhone(username)
                .orElseThrow(() -> new UsernameNotFoundException("Such username doesn't exists" + username));
    }

    @Override
    public Car updateCar(CarDTO carDTO, Principal principal) {
        User user = getUserFromPrincipal(principal);
        Car car = carRepository.getCarById(carDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Such car doesn't exists"));
        if (car.getOwnerId().equals(user.getId())) {
            car.setDescription(carDTO.getDescription());
            car.setMark(carDTO.getMark());
            car.setMileage(carDTO.getMileage());
            car.setManYear(carDTO.getMan_year());
            car.setUpdatedDate(LocalDateTime.now());
            log.info("updating car with id {}", car.getId());
            return carRepository.save(car);
        } else {
            log.error("Access denied");
            throw new AccessDeniedException("You don't have access to this car");
        }
    }

    @Override
    public void deleteCar(CarDTO carDTO, Principal principal) {
        User owner = getUserFromPrincipal(principal);
        Car car = carRepository.getCarById(carDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Such car doesn't exists"));
        if (car.getOwnerId().equals(owner.getId())) {
            log.info("Deleting a car with id {}", car.getId());
            carRepository.delete(car);
        } else {
            log.error("Access denied");
            throw new AccessDeniedException("You don't have access to this car");
        }
    }

    @Override
    public Car getInformationAboutCar(CarDTO carDTO) {
        log.info("retrivieng information about car with id {}", carDTO.getCarId());
        return carRepository.getCarById(carDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car with such id doesn't exists"));
    }

}
