import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Satellite} from '../models/satellite.model';
import {OrbitPoint} from '../models/orbit-point.model';

@Injectable({
  providedIn: 'root'
})
export class SatelliteService {
  private readonly apiUrl = 'http://localhost:8080/api/satellites'

  constructor(private http: HttpClient) { }

  getAllSatellites(): Observable<Satellite[]> {
    return this.http.get<Satellite[]>(this.apiUrl);
  }

  getOrbit(noradId: string, hours = 24, stepMinutes = 30): Observable<OrbitPoint[]> {
    return this.http.get<OrbitPoint[]>(
      `${this.apiUrl}/${noradId}/orbit?hours=${hours}&stepMinutes=${stepMinutes}`
    );
  }
}
