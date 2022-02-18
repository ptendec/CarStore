package com.tech.leasing.repository;

import com.tech.leasing.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> getCarById(Long id);
}
