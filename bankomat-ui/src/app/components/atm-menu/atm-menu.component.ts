import { Component } from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations';
import { WithdrawalResponse } from 'src/app/models/withdrawal-response';

@Component({
  selector: 'app-atm-menu',
  templateUrl: './atm-menu.component.html',
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
export class AtmMenuComponent {
  showWithdrawal: boolean = false;
  showCardEjection: boolean = false;
  withdrawalObject: WithdrawalResponse = new WithdrawalResponse();

  constructor() {}

  startWithdrawal() {
    this.showWithdrawal = true;
  }

  startEjection() {
    this.showCardEjection = true;
  }
}
