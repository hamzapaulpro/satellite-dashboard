import {Component, ElementRef, Input, ViewChild, OnChanges, SimpleChanges} from '@angular/core';
import {SatelliteService} from '../../services/satellite.service';
import {OrbitPoint} from '../../models/orbit-point.model';
import {CommonModule} from '@angular/common';
import * as Plotly from 'plotly.js-dist-min';
import { PlotData, Layout } from 'plotly.js';

@Component({
  selector: 'app-orbit-chart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './orbit-chart.component.html',
  styleUrl: './orbit-chart.component.scss'
})
export class OrbitChartComponent implements OnChanges {
  @Input() noradId: string | null = null;
  @ViewChild('chartDiv', {static: true}) chartDiv!: ElementRef<HTMLDivElement>
  loading = false

  constructor(private satelliteService: SatelliteService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['noradId'] && this.noradId) {
      this.loadOrbit(this.noradId)
    }
  }


  private loadOrbit(noradId: string) {
    this.loading = true
    this.satelliteService.getOrbit(noradId).subscribe(
      {
        next: (points) => {
        this.renderChart(points)
        this.loading = false
        },
        error: (err) => {
          console.error('Failed to load orbit data', err);
          this.loading = false;
        }
      }
    )

  }

  private renderChart(points: OrbitPoint[]): void {
    const timestamps = points.map(p => p.timestamp);
    const altitudes = points.map(p => p.altitudeKm);
    const velocities = points.map(p => p.velocityKmS);

    const altitudeTrace: Partial<PlotData> = {
      x: timestamps,
      y: altitudes,
      type: 'scatter',
      mode: 'lines',
      name: 'Altitude (km)',
      yaxis: 'y1'
    };

    const velocityTrace: Partial<PlotData> = {
      x: timestamps,
      y: velocities,
      type: 'scatter',
      mode: 'lines',
      name: 'Velocity (km/s)',
      yaxis: 'y2'
    };

    const layout: Partial<Layout> = {
      title: { text: `Orbit Profile — ${this.noradId}` },
      xaxis: { title: { text: 'Time' } },
      yaxis: { title: { text: 'Altitude (km)' }, side: 'left' },
      yaxis2: {
        title: { text: 'Velocity (km/s)' },
        side: 'right',
        overlaying: 'y'
      },
      margin: { t: 50, r: 60, l: 60, b: 50 }
    };

    Plotly.newPlot(this.chartDiv.nativeElement, [altitudeTrace, velocityTrace], layout, {
      responsive: true
    });
  }
}
