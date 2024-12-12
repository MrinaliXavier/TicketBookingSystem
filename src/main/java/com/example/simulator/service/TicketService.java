package com.example.simulator.service;

import com.example.simulator.component.TicketPool;
import com.example.simulator.entity.Event;
import com.example.simulator.entity.SystemConfiguration;
import com.example.simulator.entity.Ticket;
import com.example.simulator.repository.EventRepository;
import com.example.simulator.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SystemConfigurationService systemConfigurationService;

    @Autowired
    TicketRepository ticketRepository;

    public void purchaseTickets(Long eventId, Integer userId, int quantity) throws InterruptedException {
        // Fetch the running configuration to validate ticket limits
        SystemConfiguration config = systemConfigurationService.getRunningConfiguration();
        if (config == null || !config.isRunning()) {
            throw new IllegalStateException("System is not running. Cannot create events.");
        }

        // Validate event
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found."));

        if (event.getNoOfTickets() < quantity) {
            throw new IllegalStateException("Not enough tickets available.");
        }

        // Update event ticket count
        event.setNoOfTickets(event.getNoOfTickets() - quantity);
        eventRepository.save(event);

        // Purchase tickets from the ticket pool
        ticketPool.purchaseTicket(eventId, userId, quantity);

    }

    public List<Ticket> getAvailableTickets(Long eventId, int quantity) {
        Pageable pageable = PageRequest.of(0, quantity);
        return ticketRepository.findTopNAvailableTickets(eventId, pageable);
    }
}
