package com.example.cricketbooking;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.math.BigDecimal;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ground_id")
    private Ground ground;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String groundName;

    private LocalDate date;
    private LocalTime time;

    private String slot;

    @Column(nullable = false)
    private Integer durationHours = 1;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerHour = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    private String userEmail;

    private String userMobile;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    private boolean paymentEnabled;

    private Boolean paymentCompleted = false;

    private LocalDateTime requestedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime confirmedAt;

    private LocalDateTime paidAt;

    // Getters & Setters
    public Long getId() { return id; }

    public Ground getGround() { return ground; }
    public void setGround(Ground ground) { this.ground = ground; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getGroundName() { return groundName; }
    public void setGroundName(String groundName) { this.groundName = groundName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public String getSlot() { return slot; }
    public void setSlot(String slot) { this.slot = slot; }

    public Integer getDurationHours() { return durationHours; }
    public void setDurationHours(Integer durationHours) { this.durationHours = durationHours; }

    public BigDecimal getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(BigDecimal pricePerHour) { this.pricePerHour = pricePerHour; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserMobile() { return userMobile; }
    public void setUserMobile(String userMobile) { this.userMobile = userMobile; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public boolean isPaymentEnabled() { return paymentEnabled; }
    public void setPaymentEnabled(boolean paymentEnabled) { this.paymentEnabled = paymentEnabled; }

    public boolean isPaymentCompleted() { return Boolean.TRUE.equals(paymentCompleted); }
    public void setPaymentCompleted(Boolean paymentCompleted) { this.paymentCompleted = paymentCompleted; }

    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }

    public LocalDateTime getConfirmedAt() { return confirmedAt; }
    public void setConfirmedAt(LocalDateTime confirmedAt) { this.confirmedAt = confirmedAt; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
}
