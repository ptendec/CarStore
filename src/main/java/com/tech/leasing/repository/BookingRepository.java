package com.tech.leasing.repository;

import com.tech.leasing.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> getBookingById(Long id);
}
