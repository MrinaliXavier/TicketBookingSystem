package com.example.simulator.repository;

import com.example.simulator.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventName(String eventName);
    List<Event> findByUserId(Integer userId);
}
