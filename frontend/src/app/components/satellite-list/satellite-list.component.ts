import {Component, OnInit} from '@angular/core';
import { Satellite } from '../../models/satellite.model';
import { SatelliteService } from '../../services/satellite.service';
import {CommonModule} from '@angular/common';
import {TableModule} from 'primeng/table';
import {OrbitChartComponent} from '../orbit-chart/orbit-chart.component';

@Component({
  selector: 'app-satellite-list',
  standalone: true,
  imports: [CommonModule, TableModule, OrbitChartComponent],
  templateUrl: './satellite-list.component.html',
  styleUrl: './satellite-list.component.scss'
})
export class SatelliteListComponent implements OnInit {
  satellites: Satellite[] = [];
  selectedNoradId: string | null = null;
  loading = true;

  constructor(private satelliteService: SatelliteService) {}

  ngOnInit(): void {
    this.satelliteService.getAllSatellites().subscribe(
      {
        next: (data) => {
          this.satellites = data
          this.loading = false
        },
        error: (err) => {
          console.error("Failed to load satellites", err)
          this.loading = false
        }
      }
    );
  }

  selectSatellite(sat: Satellite): void {
    this.selectedNoradId = sat.noradId;
  }
}
