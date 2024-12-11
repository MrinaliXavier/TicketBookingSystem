import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private availableTicketsSubject = new BehaviorSubject<number>(0);
  availableTickets$ = this.availableTicketsSubject.asObservable();

  constructor() {}

  updateAvailableTickets(tickets: number) {
    this.availableTicketsSubject.next(tickets);
  }

  addTickets(tickets: number) {
    const currentTickets = this.availableTicketsSubject.value;
    this.updateAvailableTickets(currentTickets + tickets);
  }

  removeTickets() {
    const currentTickets = this.availableTicketsSubject.value;
    if (currentTickets > 0) {
      this.updateAvailableTickets(currentTickets - 1);
    }
  }


  /*private availableTicketsSubject = new BehaviorSubject<number>(0);
  availableTickets$ = this.availableTicketsSubject.asObservable();

  constructor(private http: HttpClient) {
    // Poll every 5 seconds for ticket updates
    interval(5000).subscribe(() => this.fetchAvailableTickets());
  }

  fetchAvailableTickets() {
    this.http.get<number>('http://localhost:8080/tickets/available').subscribe(tickets => {
      this.availableTicketsSubject.next(tickets);
    });
  }

  addTickets(tickets: number) {
    this.availableTicketsSubject.next(this.availableTicketsSubject.value + tickets);
  }

  removeTickets() {
    const currentTickets = this.availableTicketsSubject.value;
    if (currentTickets > 0) {
      this.availableTicketsSubject.next(currentTickets - 1);
    }
  }*/
}
