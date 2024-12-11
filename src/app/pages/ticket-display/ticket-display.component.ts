import {Component, OnInit} from '@angular/core';
import {TicketService} from "../../services/ticket.service";

@Component({
  selector: 'app-ticket-display',
  templateUrl: './ticket-display.component.html',
  styleUrls: ['./ticket-display.component.css']
})
export class TicketDisplayComponent implements OnInit {
  availableTickets: number = 0;

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.ticketService.availableTickets$.subscribe(tickets => {
      this.availableTickets = tickets;
    });
  }
}
