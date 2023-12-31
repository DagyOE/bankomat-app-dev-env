import { Component, OnInit, Input } from '@angular/core';
import { Atm } from 'src/app/models/atm';
import { CardVerificationResponse } from 'src/app/models/card-verification-response';
import { trigger, style, animate, transition } from '@angular/animations';

@Component({
  selector: 'app-account-verification',
  templateUrl: './account-verification.component.html',
  animations: [
    trigger('incomingAnimation', [
      transition(':enter', [
        style({ opacity: 0, scale: 0.5 }),
        animate(
          '0.5s ease',
          style({
            opacity: 1,
            scale: 1,
          })
        ),
      ]),
      transition(':leave', [
        style({ opacity: 1, scale: 1 }),
        animate(
          '0.5s ease',
          style({
            opacity: 0,
            scale: 0.5,
          })
        ),
      ]),
    ]),
  ],
})
export class AccountVerificationComponent implements OnInit {
  @Input() cardVerification: CardVerificationResponse =
    new CardVerificationResponse();

  placeholder: boolean = true;

  constructor() {}

  ngOnInit() {
    setTimeout(() => {
      this.placeholder = false;
    }, 2000);
  }
}
