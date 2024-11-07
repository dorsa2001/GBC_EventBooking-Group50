package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
    Optional<BookingResponse> getBookingById(String id);
    List<BookingResponse> getAllBookings();
    void deleteBooking(String id);
}
