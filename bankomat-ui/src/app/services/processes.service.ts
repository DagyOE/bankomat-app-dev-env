import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Atm } from '../models/atm';

@Injectable({
  providedIn: 'root'
})
export class ProcessesService {

  private BASE_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAtmId(): Observable<any> {
    const url = `${this.BASE_URL}/bankomat-bff/v1/bff-api/atm/get-all`;
    return this.http.get(url);
  } 
}
