import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Atm } from '../models/atm';
import { CardVerificationResponse } from '../models/card-verification-response';
import { CardVerificationRequest } from '../models/card-verification-request';
import { WithdrawalRequest } from '../models/withdrawal-request';
import {CardEjectRequest} from "../models/card-eject-request";

@Injectable({
  providedIn: 'root',
})
export class ProcessesService {
  private BASE_URL = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getAtmId(): Observable<any> {
    const url = `${this.BASE_URL}/bankomat-bff/v1/bff-api/atm/get-all`;
    return this.http.get(url);
  }

  cardVerification(
    cardVerificationRequest: CardVerificationRequest
  ): Observable<CardVerificationResponse> {
    const url = `${this.BASE_URL}/bankomat-bff/v1/bff-api/card-verification/`;
    return this.http.post(url, cardVerificationRequest);
  }

  cardVerificationCallback(transactionId: string | undefined) {
    const url = `${this.BASE_URL}/bankomat-bff/v1/bff-api/callback/card-verification/${transactionId}`;
    return this.http.get(url);
  }

  withdrawal(withdrawalRequest: WithdrawalRequest) {
    const url = `${this.BASE_URL}/bankomat-bff/v1/bff-api/withdrawal/`;
    return this.http.post(url, withdrawalRequest);
  }

  withdrawalCallback(transactionId: string | undefined) {
    const url = `${this.BASE_URL}/bankomat-bff/v1/bff-api/callback/withdrawal/${transactionId}`;
    return this.http.get(url);
  }

  cardEject(cardEjectRequest: CardEjectRequest): Observable<CardVerificationResponse> {
    const url = `${this.BASE_URL}/bankomat-bff/v1/bff-api/card-eject/`;
    return this.http.post(url, cardEjectRequest);
  }

  cardEjectCallback(transactionId: string | undefined) {
    const url = `${this.BASE_URL}/bankomat-bff/v1/bff-api/callback/card-eject/${transactionId}`;
    return this.http.get(url);
  }
}
