import {Component, Input} from '@angular/core';
import {CallbackResponse} from "../../models/callback-response";
import {animate, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-modal-window',
  templateUrl: './modal-window.component.html',
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
export class ModalWindowComponent {
  @Input() isProcessing = false;
  @Input() successProcessing = false;
  @Input() failedProcessing = false;
  @Input() callbackResponse!: CallbackResponse;
}
