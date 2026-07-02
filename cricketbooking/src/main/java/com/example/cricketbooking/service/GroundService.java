package com.example.cricketbooking.service;

import com.example.cricketbooking.Ground;
import com.example.cricketbooking.GroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroundService {

    @Autowired
    private GroundRepository groundRepository;

    public List<Ground> getAllGrounds() {
        return groundRepository.findAllByOrderByNameAsc();
    }

    public Ground getGroundById(Long id) {
        return groundRepository.findById(id).orElse(null);
    }

    public void saveGround(Ground ground) {
        groundRepository.save(ground);
    }

    public void deleteGround(Long id) {
        groundRepository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return groundRepository.existsByNameIgnoreCase(name);
    }

    public long countGrounds() {
        return groundRepository.count();
    }
}
