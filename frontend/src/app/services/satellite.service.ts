import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Satellite} from '../models/satellite.model';

@Injectable({
  providedIn: 'root'
})
export class SatelliteService {
  private readonly apiUrl = 'http://localhost:8080/api/satellites'

  constructor(private http: HttpClient) { }

  getAllSatellites(): Observable<Satellite[]> {
    return this.http.get<Satellite[]>(this.apiUrl);
  }
}
