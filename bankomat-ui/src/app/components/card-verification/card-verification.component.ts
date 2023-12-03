import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Atm } from 'src/app/models/atm';
import { CardVerificationRequest } from 'src/app/models/card-verification-request';
import { CardVerificationResponse } from 'src/app/models/card-verification-response';
import { ProcessesService } from 'src/app/services/processes.service';
import { v4 as uuid } from 'uuid';
import { trigger, style, animate, transition } from '@angular/animations';

@Component({
  selector: 'app-card-verification',
  templateUrl: './card-verification.component.html',
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
export class CardVerificationComponent implements OnInit {
  showAccountVerificationComponent: boolean = false;
  atm: Atm = new Atm();
  cardVerification: CardVerificationRequest = new CardVerificationRequest();
  cardVerificationResponse: CardVerificationResponse =
    new CardVerificationResponse();

  constructor(
    private processesService: ProcessesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.showAccountVerificationComponent = false;
    this.processesService.getAtmId().subscribe((data: any[]) => {
      this.atm.atmId = data[0].id;
      localStorage.setItem('atmId', this.atm.atmId!);
    });
  }

  verificateCard() {
    this.cardVerification.atmId = this.atm.atmId;
    this.cardVerification.transactionId = uuid();
    this.cardVerification.requestTime = new Date();

    this.processesService
      .cardVerification(this.cardVerification)
      .subscribe((data) => {
        this.cardVerificationResponse = data;
        this.showAccountVerificationComponent = true;

        setTimeout(() => {
          if (this.cardVerificationResponse.status == 'success') {
            localStorage.setItem(
              'accountId',
              this.cardVerificationResponse.accountId!
            );
            localStorage.setItem('token', this.cardVerificationResponse.token!);
            this.router.navigate(['atm']);
          } else {
            this.showAccountVerificationComponent = false;
          }
        }, 5000);
      });
  }
}
