import { Component, OnInit, Input } from '@angular/core';
import { CardVerificationResponse } from 'src/app/models/card-verification-response';

@Component({
  selector: 'app-account-verification',
  templateUrl: './account-verification.component.html'
})
export class AccountVerificationComponent implements OnInit {

  @Input() atmId = null;

  cardVerification: CardVerificationResponse = new CardVerificationResponse();

  constructor() {}

  ngOnInit() {}

  onSubmit(): void {
    console.log(this.cardVerification);
  }
}
