package com.example.cricketbooking.controller;

import com.example.cricketbooking.Booking;
import com.example.cricketbooking.BookingStatus;
import com.example.cricketbooking.Ground;
import com.example.cricketbooking.User;
import com.example.cricketbooking.service.BookingService;
import com.example.cricketbooking.service.GroundService;
import com.example.cricketbooking.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private GroundService groundService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        String sessionName = (String) session.getAttribute("sessionName");
        String sessionEmail = (String) session.getAttribute("sessionEmail");

        List<Booking> bookings = bookingService.getUserBookings(userId);
        int totalBookings = bookings != null ? bookings.size() : 0;

        LocalDate today = LocalDate.now();
        int upcomingBookings = 0;
        Booking nextBooking = null;

        if (bookings != null) {
            for (Booking b : bookings) {
                if (b.getDate() != null
                        && !b.getDate().isBefore(today)
                        && b.getStatus() != BookingStatus.REJECTED
                        && b.getStatus() != BookingStatus.CANCELLED) {
                    upcomingBookings++;
                    if (nextBooking == null) {
                        nextBooking = b;
                    }
                }
            }
        }

        BigDecimal totalSpent = bookingService.getUserTotalPaid(userId);
        long pendingRequests = bookings == null ? 0 : bookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.PENDING)
                .count();
        long approvedRequests = bookings == null ? 0 : bookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.APPROVED)
                .count();

        model.addAttribute("sessionName", sessionName != null ? sessionName : "User");
        model.addAttribute("sessionEmail", sessionEmail != null ? sessionEmail : "");
        model.addAttribute("totalBookings", totalBookings);
        model.addAttribute("upcomingBookings", upcomingBookings);
        model.addAttribute("totalSpent", totalSpent);
        model.addAttribute("nextBooking", nextBooking);
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("approvedRequests", approvedRequests);

        return "dashboard";
    }

    @GetMapping("/book")
    public String bookPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        String sessionEmail = (String) session.getAttribute("sessionEmail");
        List<Ground> grounds = groundService.getAllGrounds();

        model.addAttribute("sessionEmail", sessionEmail);
        model.addAttribute("grounds", grounds);

        return "booking";
    }

    @PostMapping("/book")
    public String book(@RequestParam Long groundId,
                       @RequestParam String date,
                       @RequestParam String time,
                       @RequestParam Integer durationHours,
                       HttpSession session,
                       Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Ground ground = groundService.getGroundById(groundId);
        if (ground == null) {
            model.addAttribute("error", "Ground not found");
            return bookPage(session, model);
        }

        LocalDate bookingDate;
        LocalTime bookingTime;
        try {
            bookingDate = LocalDate.parse(date);
            bookingTime = LocalTime.parse(time);
        } catch (DateTimeParseException ex) {
            model.addAttribute("error", "Enter a valid booking date and time");
            return bookPage(session, model);
        }

        if (durationHours == null || durationHours < 1) {
            model.addAttribute("error", "Duration must be at least 1 hour");
            return bookPage(session, model);
        }

        if (bookingDate.isBefore(LocalDate.now())) {
            model.addAttribute("error", "Booking date cannot be in the past");
            return bookPage(session, model);
        }

        String slot = date + "_" + time;

        // Check for conflicts
        if (bookingService.existsSlotConflict(groundId, bookingDate, slot)) {
            model.addAttribute("error", "This slot is already booked");
            return bookPage(session, model);
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        BigDecimal totalPrice = ground.getPricePerHour()
                .multiply(BigDecimal.valueOf(durationHours));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setGround(ground);
        booking.setGroundName(ground.getName());
        booking.setUserEmail(user.getEmail());
        booking.setUserMobile(user.getMobileNumber());
        booking.setDate(bookingDate);
        booking.setTime(bookingTime);
        booking.setSlot(slot);
        booking.setDurationHours(durationHours);
        booking.setPricePerHour(ground.getPricePerHour());
        booking.setTotalPrice(totalPrice);

        bookingService.createBookingRequest(booking);

        return "redirect:/mybookings";
    }

    @GetMapping("/mybookings")
    public String myBookings(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        String sessionEmail = (String) session.getAttribute("sessionEmail");
        List<Booking> bookings = bookingService.getUserBookings(userId);
        BigDecimal totalSpent = bookingService.getUserTotalPaid(userId);
        bookings.sort(Comparator
                .comparing(Booking::getDate, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Booking::getTime, Comparator.nullsLast(Comparator.naturalOrder())));

        model.addAttribute("sessionEmail", sessionEmail);
        model.addAttribute("bookings", bookings);
        model.addAttribute("totalSpent", totalSpent);

        return "mybookings";
    }

    @GetMapping("/confirm/{id}")
    public String confirmBooking(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Booking booking = bookingService.getBookingById(id);
        if (booking == null || booking.getUser() == null || !userId.equals(booking.getUser().getId())) {
            return "redirect:/mybookings";
        }

        if (booking.getStatus() == BookingStatus.APPROVED) {
            bookingService.confirmBooking(id);
        }

        return "redirect:/mybookings";
    }

    @GetMapping("/pay/{id}")
    public String payForBooking(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Booking booking = bookingService.getBookingById(id);
        if (booking == null || booking.getUser() == null || !userId.equals(booking.getUser().getId())) {
            return "redirect:/mybookings";
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED && booking.isPaymentEnabled() && !booking.isPaymentCompleted()) {
            bookingService.completePayment(id);
        }

        return "redirect:/mybookings";
    }

    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            Booking booking = bookingService.getBookingById(id);
            if (booking != null
                    && booking.getUser() != null
                    && userId.equals(booking.getUser().getId())
                    && booking.getStatus() != BookingStatus.COMPLETED) {
                bookingService.cancelBooking(id);
            }
        } catch (Exception e) {
            // Log error but don't crash
            System.err.println("Error cancelling booking: " + e.getMessage());
        }

        return "redirect:/mybookings";
    }
}
