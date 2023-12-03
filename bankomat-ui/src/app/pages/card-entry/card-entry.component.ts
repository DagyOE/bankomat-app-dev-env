import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Atm } from 'src/app/models/atm';
import { ProcessesService } from 'src/app/services/processes.service';

@Component({
  selector: 'app-card-entry',
  templateUrl: './card-entry.component.html',
})
export class CardEntryComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
