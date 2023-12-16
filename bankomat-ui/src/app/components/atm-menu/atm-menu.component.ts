import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations';
import {DataService} from "../../services/data.service";
import {Subscription} from "rxjs";
import {CardEjectRequest} from "../../models/card-eject-request";
import { v4 as uuid } from 'uuid';
import {ProcessesService} from "../../services/processes.service";
import {CallbackResponse} from "../../models/callback-response";
import {WebSocketService} from "../../services/web-socket.service";
import {Router} from "@angular/router";

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
export class AtmMenuComponent implements OnInit, OnDestroy{
  showWithdrawal: boolean = false;

  atmId?: string;
  accountId?: string;
  token?: string;
  isProcessing = false;
  successProcessing = false;
  failedProcessing = false;
  private messageSubscription!: Subscription;
  cardEject: CardEjectRequest = new CardEjectRequest();
  callbackResponse: CallbackResponse = new CallbackResponse();

  constructor(
    private dataService: DataService,
    private processesService: ProcessesService,
    private webSocket: WebSocketService,
    private router: Router,
    private changeDetector: ChangeDetectorRef
  ) {
    this.messageSubscription = this.webSocket.getMessage().subscribe((message: string) => {
      if (message.includes("completed:" + this.cardEject.transactionId)) {
        setTimeout(() => {
          this.processesService.cardEjectCallback(this.cardEject.transactionId)
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
                }, 3000);
              }
            });
        }, 3000);
      }
    });
  }

  ngOnInit() {
    this.dataService.currentData.subscribe(data => {
      this.atmId = data.atmId;
      this.accountId = data.data.accountId;
      this.token = data.data.token;
    });
    if (this.token == null || this.accountId == null) {
      this.router.navigate(['']);
    }
  }

  ngOnDestroy() {
    this.messageSubscription.unsubscribe();
  }

  startWithdrawal() {
    this.showWithdrawal = true;
    this.changeDetector.detectChanges();
  }

  startEjection() {
    this.isProcessing = true;

    this.cardEject.transactionId = uuid();
    this.cardEject.accountId = this.accountId;

    this.processesService
      .cardEject(this.cardEject)
      .subscribe();
  }
}
