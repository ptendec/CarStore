package com.tech.leasing.repository;

import com.tech.leasing.model.BookingStatusDict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingStatusRepository extends JpaRepository<BookingStatusDict, Long> {
}
