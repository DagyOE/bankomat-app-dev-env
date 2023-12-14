import { Component, OnInit } from '@angular/core';
import { Atm } from 'src/app/models/atm';
import { WithdrawalRequest } from 'src/app/models/withdrawal-request';
import { WithdrawalResponse } from 'src/app/models/withdrawal-response';
import { trigger, style, animate, transition } from '@angular/animations';
import { v4 as uuid } from 'uuid';
import { ProcessesService } from 'src/app/services/processes.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cash-withdrawal',
  templateUrl: './cash-withdrawal.component.html',
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
export class CashWithdrawalComponent implements OnInit {
  showCardEjectionComponent: boolean = false;

  withdrawalResponse: WithdrawalResponse = new WithdrawalResponse();
  withdrawalRequest: WithdrawalRequest = new WithdrawalRequest();

  constructor(
    private processesService: ProcessesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.withdrawalRequest.accountId = localStorage.getItem('accountId')!;
    this.withdrawalRequest.atmId = localStorage.getItem('atmId')!;
    this.withdrawalRequest.token = localStorage.getItem('token')!;
  }

  withdrawalMoney() {
    this.withdrawalRequest.transactionId = uuid();
    this.withdrawalRequest.currency = 'CZK';

    this.processesService
      .withdrawal(this.withdrawalRequest)
      .subscribe((data) => {
        this.withdrawalResponse = data;
        console.log(this.withdrawalResponse);
        this.showCardEjectionComponent = true;
        setTimeout(() => {
          this.router.navigate(['']);
        }, 5000);
      });
  }
}
