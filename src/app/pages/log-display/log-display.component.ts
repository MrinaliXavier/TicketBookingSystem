import { Component } from '@angular/core';
import {NgFor} from "@angular/common";

@Component({
  selector: 'app-log-display',
  templateUrl: './log-display.component.html',
  styleUrls: ['./log-display.component.css']
})
export class LogDisplayComponent {

  logs: string[] = [];

  ngOnInit(): void {
    // For demonstration, simulate logs being added
    setInterval(() => {
      this.addLog(`New log entry at ${new Date().toLocaleTimeString()}`);
    }, 5000);
  }

  addLog(message: string) {
    this.logs.push(message);
    if (this.logs.length > 10) {
      this.logs.shift(); // Keep only the last 10 logs
    }
  }
}
