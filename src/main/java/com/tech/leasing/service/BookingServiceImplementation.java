package com.tech.leasing.service;

import com.tech.leasing.dto.BookingDTO;
import com.tech.leasing.model.Booking;
import com.tech.leasing.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImplementation implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;


    @Override
    public Booking sendBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setCarId(bookingDTO.getCarId());
        booking.setUserId(bookingDTO.getUserId());
        bookingRepository.save(booking);
        return null;
    }


}
