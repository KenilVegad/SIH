package com.example.cricketbooking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroundRepository extends JpaRepository<Ground, Long> {

    boolean existsByNameIgnoreCase(String name);

    List<Ground> findAllByOrderByNameAsc();
}
