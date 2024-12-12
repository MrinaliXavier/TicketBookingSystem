package com.example.simulator.dto;

import com.example.simulator.enums.EventCategory;
import com.example.simulator.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventRequest {
    private Long eventId;
    private String eventName;
    private int ticketPrice;
    private int noOfTickets;
    private LocalDate date;
    private Integer userId;
    private String venue;
    private String description;
    private EventCategory category;
    private EventStatus status;
}