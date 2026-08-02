import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {SatelliteListComponent} from './components/satellite-list/satellite-list.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, SatelliteListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
