import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WithdrawalResponse } from 'src/app/models/withdrawal-response';
import { trigger, style, animate, transition } from '@angular/animations';

@Component({
  selector: 'app-card-ejection',
  templateUrl: './card-ejection.component.html',
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
export class CardEjectionComponent implements OnInit {
  @Input() withdrawalResponse: WithdrawalResponse = new WithdrawalResponse();
  status?: string;
  message?: string;

  placeholder: boolean = true;

  constructor(private router: Router) {}

  ngOnInit(): void {
    if (Object.keys(this.withdrawalResponse).length === 0) {
      this.status = 'success';
      this.message =
        'Start card ejecting from atm, thank you for your transaction';
    } else {
      this.status = this.withdrawalResponse.status;
      this.message = this.withdrawalResponse.message;
    }

    setTimeout(() => {
      this.placeholder = false;
    }, 2000);

    localStorage.clear();

    setTimeout(() => {
      this.router.navigate(['']);
    }, 5000);
  }
}
