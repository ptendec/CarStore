package com.tech.leasing.service;

import com.tech.leasing.dto.BookingDTO;
import com.tech.leasing.model.Booking;


public interface BookingService {
    Booking sendBooking(BookingDTO bookingDTO);

}
