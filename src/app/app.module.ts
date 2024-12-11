import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ConfigurationFormComponent} from "./pages/configuration-form/configuration-form.component";
import {ControlPanelComponent} from "./pages/control-panel/control-panel.component";
import {TicketDisplayComponent} from "./pages/ticket-display/ticket-display.component";
import {LogDisplayComponent} from "./pages/log-display/log-display.component";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    ConfigurationFormComponent,
    ControlPanelComponent,
    TicketDisplayComponent,
    LogDisplayComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
