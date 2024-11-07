package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Booking booking = Booking.builder()
                .userId(bookingRequest.getUserId())
                .roomId(bookingRequest.getRoomId())
                .startTime(bookingRequest.getStartTime())
                .endTime(bookingRequest.getEndTime())
                .purpose(bookingRequest.getPurpose())
                .build();

        booking = bookingRepository.save(booking);
        return mapToBookingResponse(booking);
    }

    @Override
    public Optional<BookingResponse> getBookingById(String id) {
        return bookingRepository.findById(id).map(this::mapToBookingResponse);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }

    private BookingResponse mapToBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .roomId(booking.getRoomId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .purpose(booking.getPurpose())
                .build();
    }
}
