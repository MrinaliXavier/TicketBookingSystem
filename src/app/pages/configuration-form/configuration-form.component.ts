import {Component, Output ,EventEmitter} from '@angular/core';
import {FormsModule, NgModel} from "@angular/forms";

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css']
})
export class ConfigurationFormComponent {
  totalTickets: number = 100;
  poolCapacity : number = 50;
  ticketReleaseRate: number = 5;
  customerRetrievalRate: number = 3;
  vendorCount: number = 3;
  customerCount: number = 2;
  

  @Output() configChange = new EventEmitter<any>();

  onSave() {
    const config = {
      totalTickets: this.totalTickets,
      poolCapacity: this.poolCapacity,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate,
      vendorCount: this.vendorCount,
      customerCount: this.customerCount

    };
    this.configChange.emit(config);
  }
}
