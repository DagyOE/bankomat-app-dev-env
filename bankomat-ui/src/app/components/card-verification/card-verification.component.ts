import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { CardVerificationRequest } from 'src/app/models/card-verification-request';

@Component({
  selector: 'app-card-verification',
  templateUrl: './card-verification.component.html'
})
export class CardVerificationComponent implements OnInit {

  cardVerification: CardVerificationRequest = new CardVerificationRequest();

  constructor( ) {}

  ngOnInit(): void {
    
  }
}
