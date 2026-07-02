package com.example.cricketbooking.service;

import com.example.cricketbooking.Booking;
import com.example.cricketbooking.BookingRepository;
import com.example.cricketbooking.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserIdOrderByDateAscTimeAsc(userId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByDateAscTimeAsc();
    }

    public List<Booking> getRecentBookings(int limit) {
        return bookingRepository.findTop5ByOrderByDateDescTimeDesc();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void createBookingRequest(Booking booking) {
        booking.setStatus(BookingStatus.PENDING);
        booking.setPaymentEnabled(false);
        booking.setPaymentCompleted(false);
        booking.setRequestedAt(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    public void cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        if (booking != null) {
            booking.setStatus(BookingStatus.CANCELLED);
            booking.setPaymentEnabled(false);
            bookingRepository.save(booking);
        }
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public boolean existsSlotConflict(Long groundId, LocalDate date, String slot) {
        return bookingRepository.existsActiveSlotConflict(groundId, date, slot);
    }

    public boolean existsSlotConflictExcludingBooking(Long bookingId, Long groundId, LocalDate date, String slot) {
        return bookingRepository.existsActiveSlotConflictExcludingBooking(bookingId, groundId, date, slot);
    }

    public long countBookings() {
        return bookingRepository.count();
    }

    public long countPendingBookings() {
        return bookingRepository.countByStatus(BookingStatus.PENDING);
    }

    public long countApprovedBookings() {
        return bookingRepository.countByStatus(BookingStatus.APPROVED);
    }

    public long countPaidBookings() {
        return bookingRepository.countByStatus(BookingStatus.COMPLETED);
    }

    public BigDecimal getTotalRevenue() {
        return bookingRepository.sumTotalRevenue();
    }

    public long countBookingsForGround(Long groundId) {
        return bookingRepository.countByGroundId(groundId);
    }

    public BigDecimal getUserTotalPaid(Long userId) {
        BigDecimal total = BigDecimal.ZERO;
        for (Booking booking : getUserBookings(userId)) {
            if (booking.isPaymentCompleted() && booking.getTotalPrice() != null) {
                total = total.add(booking.getTotalPrice());
            }
        }
        return total;
    }

    public Booking approveBooking(Long id) {
        Booking booking = getBookingById(id);
        if (booking == null) {
            return null;
        }

        booking.setStatus(BookingStatus.APPROVED);
        booking.setPaymentEnabled(true);
        booking.setApprovedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    public Booking rejectBooking(Long id) {
        Booking booking = getBookingById(id);
        if (booking == null) {
            return null;
        }

        booking.setStatus(BookingStatus.REJECTED);
        booking.setPaymentEnabled(false);
        return bookingRepository.save(booking);
    }

    public Booking confirmBooking(Long id) {
        Booking booking = getBookingById(id);
        if (booking == null) {
            return null;
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setConfirmedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    public Booking completePayment(Long id) {
        Booking booking = getBookingById(id);
        if (booking == null) {
            return null;
        }

        booking.setPaymentCompleted(true);
        booking.setStatus(BookingStatus.COMPLETED);
        booking.setPaidAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }
}
