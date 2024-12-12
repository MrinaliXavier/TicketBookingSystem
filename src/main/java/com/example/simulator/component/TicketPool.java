package com.example.simulator.component;

import com.example.simulator.entity.Event;
import com.example.simulator.entity.Ticket;
import com.example.simulator.enums.TicketStatus;
import com.example.simulator.repository.EventRepository;
import com.example.simulator.repository.TicketRepository;
import com.example.simulator.repository.UserRepository;
import com.example.simulator.service.SystemConfigurationService;
import com.example.simulator.entity.Ticket;
import com.example.simulator.enums.TicketStatus;
import com.example.simulator.repository.EventRepository;
import com.example.simulator.repository.TicketRepository;
import com.example.simulator.repository.UserRepository;
import com.example.simulator.service.SystemConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketPool {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SystemConfigurationService systemConfigurationService;

    @Autowired
    private EventRepository eventRepository;

    public synchronized void addTickets(int numberOfTickets, Long eventId) {
        while (systemConfigurationService.getRunningConfiguration() == null) {
            try {
                Thread.sleep(1000);
                wait(); // Wait until the system configuration is running
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Thread interrupted while waiting for system configuration.");
            }
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found."));

//        try {
//            Thread.sleep(1000); // Simulate delay after fetching event details
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }

        long currentTickets = ticketRepository.countByEventIdAndStatus(eventId, TicketStatus.AVAILABLE);

        if (currentTickets + numberOfTickets > event.getNoOfTickets()) {
            throw new IllegalStateException("Adding these tickets exceeds the event's total ticket limit.");
        }

        for (int i = 0; i < numberOfTickets; i++) {
            Ticket ticket = new Ticket();
            ticket.setEventId(eventId);
            ticket.setStatus(TicketStatus.AVAILABLE);

            try {
                Thread.sleep(1000); // Simulate delay for each ticket save operation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Exit loop on interruption
            }

            ticketRepository.save(ticket);
        }
        System.out.println(numberOfTickets + " tickets added to the pool.");
        notifyAll(); // Notify all threads that tickets have been added
    }



    public synchronized void purchaseTicket(Long eventId, Integer userId, int quantity) {
        while (systemConfigurationService.getRunningConfiguration() == null) {
            try {
                wait(); // Wait until the system configuration is running
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Thread interrupted while waiting for system configuration.");
            }
        }

        boolean userExists = userRepository.existsById(userId);
        if (!userExists) {
            throw new IllegalArgumentException("User does not exist.");
        }

        // Fetch available tickets for the event
        Pageable pageable = PageRequest.of(0, quantity); // Create Pageable for quantity
        List<Ticket> availableTickets = ticketRepository.findTopNAvailableTickets(eventId, pageable);

        if (availableTickets.size() < quantity) {
            throw new IllegalStateException("Not enough tickets available.");
        }

        for (Ticket ticket : availableTickets) {
            ticket.setStatus(TicketStatus.SOLD);
            ticket.setUserId(userId);

            // Use a thread to handle each ticket's update
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // Simulate processing delay
                    ticketRepository.save(ticket);
                    System.out.println("Ticket " + ticket.getId() + " purchased by user " + userId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread interrupted while purchasing ticket " + ticket.getId());
                }
            }).start();
        }

//        System.out.println(quantity + " tickets purchased by user " + userId + ".");
        System.out.println("Customer " + userId + " successfully purchased " + quantity + " tickets.");
        notifyAll(); // Notify other threads that tickets have been processed
    }

}

