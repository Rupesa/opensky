package com.example.repository;
import com.example.messagingstompwebsocket.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight,String>{
    
}
