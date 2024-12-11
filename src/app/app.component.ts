import { Component } from '@angular/core';
import {TicketService} from "./services/ticket.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  activeTab: string = 'config';

  constructor(private ticketService: TicketService) {}

  onConfigurationChange(config: any) {
    // Initialize ticket pool with total tickets
    this.ticketService.updateAvailableTickets(config.totalTickets);
  }

  onStartSystem() {
    // Simulate starting the system (e.g., adding tickets and customers)
    setInterval(() => {
      this.ticketService.addTickets(5); // Simulate adding 5 tickets every 5 seconds
    }, 5000);
  }

  onStopSystem() {
    // Simulate stopping the system
    console.log("System Stopped");
  }
  setActiveTab(tab: string) {
    this.activeTab = tab;
    console.log(this.activeTab , tab);
  }
}
