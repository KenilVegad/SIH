package com.example.cricketbooking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final GroundRepository groundRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(UserRepository userRepository,
                           GroundRepository groundRepository,
                           PasswordEncoder passwordEncoder,
                           JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.groundRepository = groundRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        normalizeBookingSchema();
        seedAdmin();
        seedGrounds();
    }

    private void normalizeBookingSchema() {
        execute("ALTER TABLE BOOKINGS ALTER COLUMN STATUS SET DATA TYPE VARCHAR(20)");
        execute("ALTER TABLE BOOKINGS ADD COLUMN IF NOT EXISTS PAYMENT_ENABLED BOOLEAN DEFAULT FALSE");
        execute("ALTER TABLE BOOKINGS ADD COLUMN IF NOT EXISTS PAYMENT_COMPLETED BOOLEAN DEFAULT FALSE");
        execute("ALTER TABLE BOOKINGS ADD COLUMN IF NOT EXISTS USER_MOBILE VARCHAR(255)");
        execute("ALTER TABLE BOOKINGS ADD COLUMN IF NOT EXISTS REQUESTED_AT TIMESTAMP");
        execute("ALTER TABLE BOOKINGS ADD COLUMN IF NOT EXISTS APPROVED_AT TIMESTAMP");
        execute("ALTER TABLE BOOKINGS ADD COLUMN IF NOT EXISTS CONFIRMED_AT TIMESTAMP");
        execute("ALTER TABLE BOOKINGS ADD COLUMN IF NOT EXISTS PAID_AT TIMESTAMP");
        execute("UPDATE BOOKINGS SET PAYMENT_ENABLED = FALSE WHERE PAYMENT_ENABLED IS NULL");
        execute("UPDATE BOOKINGS SET PAYMENT_COMPLETED = FALSE WHERE PAYMENT_COMPLETED IS NULL");
        execute("UPDATE BOOKINGS SET STATUS = 'COMPLETED' WHERE STATUS = 'PAID'");
    }

    private void execute(String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception ignored) {
        }
    }

    private void seedAdmin() {
        if (userRepository.findByEmail("admin@cricketbooking.com").isPresent()) {
            return;
        }

        User admin = new User();
        admin.setName("System Admin");
        admin.setEmail("admin@cricketbooking.com");
        admin.setMobileNumber("9999999999");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        userRepository.save(admin);
    }

    private void seedGrounds() {
        if (groundRepository.count() > 0) {
            return;
        }

        saveGround("Eden Turf Arena", "Kolkata", new BigDecimal("1800.00"), GroundType.TURF, "06:00-10:00, 17:00-22:00");
        saveGround("Victory Cricket Nets", "Bengaluru", new BigDecimal("1200.00"), GroundType.MAT, "07:00-11:00, 16:00-21:00");
        saveGround("Boundary Field", "Mumbai", new BigDecimal("2200.00"), GroundType.TURF, "05:30-09:30, 18:00-23:00");
    }

    private void saveGround(String name,
                            String location,
                            BigDecimal pricePerHour,
                            GroundType groundType,
                            String availableTimeSlots) {
        Ground ground = new Ground();
        ground.setName(name);
        ground.setLocation(location);
        ground.setPricePerHour(pricePerHour);
        ground.setGroundType(groundType);
        ground.setAvailableTimeSlots(availableTimeSlots);
        groundRepository.save(ground);
    }
}
