import {Component, Output,EventEmitter} from '@angular/core';

@Component({
  selector: 'app-control-panel',
  templateUrl: './control-panel.component.html',
  styleUrls: ['./control-panel.component.css']
})
export class ControlPanelComponent {

  @Output() startSystem = new EventEmitter<void>();
  @Output() stopSystem = new EventEmitter<void>();

  onStart() {
    this.startSystem.emit();
  }

  onStop() {
    this.stopSystem.emit();
  }
}
