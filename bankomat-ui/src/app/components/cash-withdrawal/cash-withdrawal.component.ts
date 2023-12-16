import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import { Atm } from 'src/app/models/atm';
import { WithdrawalRequest } from 'src/app/models/withdrawal-request';
import { WithdrawalResponse } from 'src/app/models/withdrawal-response';
import { trigger, style, animate, transition } from '@angular/animations';
import { v4 as uuid } from 'uuid';
import { ProcessesService } from 'src/app/services/processes.service';
import { Router } from '@angular/router';
import {CallbackResponse} from "../../models/callback-response";
import {Subscription} from "rxjs";
import {WebSocketService} from "../../services/web-socket.service";

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
export class CashWithdrawalComponent implements OnDestroy{

  @Input() showWithdrawal?: boolean;
  @Input() accountId?: string;
  @Input() token?: string;
  @Input() atmId?: string;
  @Output() closeWithdrawal = new EventEmitter<void>();
  private messageSubscription!: Subscription;
  callbackResponse: CallbackResponse = new CallbackResponse();
  withdrawalRequest: WithdrawalRequest = new WithdrawalRequest();
  isProcessing = false;
  successProcessing = false;
  failedProcessing = false;

  constructor(
    private processesService: ProcessesService,
    private router: Router,
    private webSocket: WebSocketService
  ) {
    this.messageSubscription = this.webSocket.getMessage().subscribe((message: string) => {
      if (message.includes("completed:" + this.withdrawalRequest.transactionId)) {
        setTimeout(() => {
          this.processesService.withdrawalCallback(this.withdrawalRequest.transactionId)
            .subscribe((data: CallbackResponse) => {
              this.callbackResponse = data;
              if (this.callbackResponse.isCompleted) {
                this.isProcessing = false;
                this.successProcessing = true;
                setTimeout(() => {
                  this.successProcessing = false;
                  this.router.navigate(['']);
                }, 3000);
              } else {
                this.isProcessing = false;
                this.failedProcessing = true;
                setTimeout(() => {
                  this.failedProcessing = false;
                  this.router.navigate(['']);
                }, 3000);
              }
            });
        }, 3000);
      }
    });
  }

  ngOnDestroy() {
    this.messageSubscription.unsubscribe();
  }

  withdrawalMoney() {
    this.isProcessing = true;

    this.withdrawalRequest.accountId = this.accountId;
    this.withdrawalRequest.atmId = this.atmId;
    this.withdrawalRequest.token = this.token;
    this.withdrawalRequest.transactionId = uuid();
    this.withdrawalRequest.currency = 'CZK';

    this.processesService
      .withdrawal(this.withdrawalRequest)
      .subscribe();
  }

  close() {
    this.closeWithdrawal.emit();
  }
}
