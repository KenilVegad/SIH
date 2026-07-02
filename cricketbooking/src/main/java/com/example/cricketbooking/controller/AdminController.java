package com.example.cricketbooking.controller;

import com.example.cricketbooking.Booking;
import com.example.cricketbooking.BookingStatus;
import com.example.cricketbooking.Ground;
import com.example.cricketbooking.GroundType;
import com.example.cricketbooking.User;
import com.example.cricketbooking.service.BookingService;
import com.example.cricketbooking.service.GroundService;
import com.example.cricketbooking.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private GroundService groundService;

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("role");
        return "ADMIN".equals(role);
    }

    @GetMapping("/dashboard")
    public String adminDashboard(@RequestParam(required = false) String error,
                                 HttpSession session,
                                 Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        String sessionName = (String) session.getAttribute("sessionName");
        String sessionEmail = (String) session.getAttribute("sessionEmail");

        long totalUsers = userService.countUsers();
        long totalBookings = bookingService.countBookings();
        long pendingBookings = bookingService.countPendingBookings();
        long approvedBookings = bookingService.countApprovedBookings();
        long paidBookings = bookingService.countPaidBookings();
        BigDecimal totalRevenue = bookingService.getTotalRevenue();
        long totalGrounds = groundService.countGrounds();
        List<Booking> recentBookings = bookingService.getRecentBookings(5);

        model.addAttribute("sessionName", sessionName);
        model.addAttribute("sessionEmail", sessionEmail);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalBookings", totalBookings);
        model.addAttribute("pendingBookings", pendingBookings);
        model.addAttribute("approvedBookings", approvedBookings);
        model.addAttribute("paidBookings", paidBookings);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalGrounds", totalGrounds);
        model.addAttribute("recentBookings", recentBookings);
        model.addAttribute("error", error);

        return "admin-dashboard";
    }

    @GetMapping("/users")
    public String adminUsers(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "admin-users";
    }

    @GetMapping("/bookings")
    public String adminBookings(@RequestParam(required = false) String error,
                                HttpSession session,
                                Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<Booking> bookings = bookingService.getAllBookings();
        BigDecimal totalRevenue = bookingService.getTotalRevenue();

        model.addAttribute("bookings", bookings);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("error", error);

        return "admin-bookings";
    }

    @GetMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        if (bookingService.getBookingById(id) == null) {
            redirectAttributes.addAttribute("error", "Booking not found");
            return "redirect:/admin/bookings";
        }

        bookingService.deleteBooking(id);
        return "redirect:/admin/bookings";
    }

    @GetMapping("/grounds")
    public String adminGrounds(@RequestParam(required = false) String error,
                               HttpSession session,
                               Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<Ground> grounds = groundService.getAllGrounds();
        model.addAttribute("grounds", grounds);
        model.addAttribute("error", error);
        model.addAttribute("groundTypes", GroundType.values());

        return "admin-grounds";
    }

    @PostMapping("/grounds")
    public String addGround(@RequestParam String name,
                            @RequestParam String location,
                            @RequestParam String availableTimeSlots,
                            @RequestParam GroundType groundType,
                            @RequestParam BigDecimal pricePerHour,
                            HttpSession session,
                            Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        if (groundService.existsByName(name)) {
            model.addAttribute("error", "Ground with this name already exists");
            model.addAttribute("grounds", groundService.getAllGrounds());
            model.addAttribute("groundTypes", GroundType.values());
            return "admin-grounds";
        }

        Ground ground = new Ground();
        ground.setName(name);
        ground.setLocation(location);
        ground.setPricePerHour(pricePerHour);
        ground.setAvailableTimeSlots(availableTimeSlots);
        ground.setGroundType(groundType);

        groundService.saveGround(ground);

        return "redirect:/admin/grounds";
    }

    @GetMapping("/bookings/approve/{id}")
    public String approveBooking(@PathVariable Long id,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            redirectAttributes.addAttribute("error", "Booking not found");
            return "redirect:/admin/bookings";
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            redirectAttributes.addAttribute("error", "Only pending requests can be approved");
            return "redirect:/admin/bookings";
        }

        if (bookingService.existsSlotConflictExcludingBooking(booking.getId(), booking.getGround().getId(), booking.getDate(), booking.getSlot())) {
            redirectAttributes.addAttribute("error", "Cannot approve because the slot is already occupied");
            return "redirect:/admin/bookings";
        }

        bookingService.approveBooking(id);
        return "redirect:/admin/bookings";
    }

    @GetMapping("/bookings/reject/{id}")
    public String rejectBooking(@PathVariable Long id,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            redirectAttributes.addAttribute("error", "Booking not found");
            return "redirect:/admin/bookings";
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            redirectAttributes.addAttribute("error", "Only pending requests can be rejected");
            return "redirect:/admin/bookings";
        }

        bookingService.rejectBooking(id);
        return "redirect:/admin/bookings";
    }

    @GetMapping("/grounds/delete/{id}")
    public String deleteGround(@PathVariable Long id,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Ground ground = groundService.getGroundById(id);
        if (ground == null) {
            redirectAttributes.addAttribute("error", "Ground not found");
            return "redirect:/admin/grounds";
        }

        if (bookingService.countBookingsForGround(id) > 0) {
            redirectAttributes.addAttribute("error", "Cannot remove a ground that already has bookings");
            return "redirect:/admin/grounds";
        }

        groundService.deleteGround(id);
        return "redirect:/admin/grounds";
    }
}
