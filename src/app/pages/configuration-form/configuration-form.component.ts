import {Component, Output ,EventEmitter} from '@angular/core';
import {FormsModule, NgModel} from "@angular/forms";

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css']
})
export class ConfigurationFormComponent {
  totalTickets: number = 100;
  ticketReleaseRate: number = 5;
  customerRetrievalRate: number = 3;

  @Output() configChange = new EventEmitter<any>();

  onSave() {
    const config = {
      totalTickets: this.totalTickets,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate
    };
    this.configChange.emit(config);
  }
}
