package com.example.cricketbooking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    java.util.List<Booking> findByUserIdOrderByDateAscTimeAsc(Long userId);

    java.util.List<Booking> findAllByOrderByDateAscTimeAsc();

    java.util.List<Booking> findTop5ByOrderByDateDescTimeDesc();

    long countByGroundId(Long groundId);

    long countByStatus(BookingStatus status);

    long countByStatusIn(java.util.Collection<BookingStatus> statuses);

    @org.springframework.data.jpa.repository.Query("""
            select case when count(b) > 0 then true else false end
            from Booking b
            where b.ground.id = :groundId
              and b.date = :date
              and b.slot = :slot
              and b.status not in (
                  com.example.cricketbooking.BookingStatus.REJECTED,
                  com.example.cricketbooking.BookingStatus.CANCELLED
              )
            """)
    boolean existsActiveSlotConflict(Long groundId, java.time.LocalDate date, String slot);

    @org.springframework.data.jpa.repository.Query("""
            select case when count(b) > 0 then true else false end
            from Booking b
            where b.ground.id = :groundId
              and b.date = :date
              and b.slot = :slot
              and b.id <> :bookingId
              and b.status not in (
                  com.example.cricketbooking.BookingStatus.REJECTED,
                  com.example.cricketbooking.BookingStatus.CANCELLED
              )
            """)
    boolean existsActiveSlotConflictExcludingBooking(Long bookingId, Long groundId, java.time.LocalDate date, String slot);

    @org.springframework.data.jpa.repository.Query("""
            select coalesce(sum(b.totalPrice), 0)
            from Booking b
            where b.paymentCompleted = true
            """)
    java.math.BigDecimal sumTotalRevenue();
}
