import {Component, OnDestroy, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { Atm } from 'src/app/models/atm';
import { CardVerificationRequest } from 'src/app/models/card-verification-request';
import { ProcessesService } from 'src/app/services/processes.service';
import { v4 as uuid } from 'uuid';
import { trigger, style, animate, transition } from '@angular/animations';
import {WebSocketService} from "../../services/web-socket.service";
import {Subscription} from "rxjs";
import {CallbackResponse} from "../../models/callback-response";
import {DataService} from "../../services/data.service";
import {CardVerificationResponse} from "../../models/card-verification-response";

@Component({
  selector: 'app-card-verification',
  templateUrl: './card-verification.component.html',
  animations: [
    trigger('incomingAnimation', [
      transition(':enter', [
        style({ opacity: 0, scale: 0 }),
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
export class CardVerificationComponent implements OnInit, OnDestroy {

  atm: Atm = new Atm();
  cardVerification: CardVerificationRequest = new CardVerificationRequest();
  callbackResponse: CallbackResponse = new CallbackResponse();
  isProcessing = false;
  successProcessing = false;
  failedProcessing = false;
  private messageSubscription!: Subscription;

  constructor(
    private processesService: ProcessesService,
    private router: Router,
    private webSocket: WebSocketService,
    private dataService: DataService
  ) {
    this.messageSubscription = this.webSocket.getMessage().subscribe((message: string) => {
      if (message.includes("completed:" + this.cardVerification.transactionId)) {
        setTimeout(() => {
          processesService.cardVerificationCallback(this.cardVerification.transactionId)
            .subscribe((data: CallbackResponse) => {
              this.callbackResponse = data;
              this.dataService.updateData({
                'atmId': this.atm.atmId,
                'data': this.callbackResponse.response
              });
              if (this.callbackResponse.isCompleted) {
                this.isProcessing = false;
                this.successProcessing = true;
                setTimeout(() => {
                  this.successProcessing = false;
                  this.router.navigate(['atm']);
                }, 3000);
              } else {
                this.isProcessing = false;
                this.failedProcessing = true;
                setTimeout(() => {
                  this.failedProcessing = false;
                }, 3000);
              }
            });
        }, 3000);
      }
    });
  }

  ngOnInit(): void {
    this.processesService.getAtmId().subscribe((data: any[]) => {
      this.atm.atmId = data[0].id;
    });
  }

  ngOnDestroy() {
    this.messageSubscription.unsubscribe();
  }

  verificateCard() {
    this.isProcessing = true;
    this.cardVerification.atmId = this.atm.atmId;
    this.cardVerification.transactionId = uuid();

    this.processesService
      .cardVerification(this.cardVerification)
      .subscribe();
  }
}
