import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Atm } from 'src/app/models/atm';
import { ProcessesService } from 'src/app/services/processes.service';

@Component({
  selector: 'app-card-entry',
  templateUrl: './card-entry.component.html'
})
export class CardEntryComponent implements OnInit {

  @Output() atmEvent = new EventEmitter<Atm>();
  atm: Atm = new Atm();

  constructor(
    private processesService: ProcessesService
  ) {}

  ngOnInit(): void {
    this.processesService.getAtmId().subscribe((data: any[]) => {
      this.atm.atmId = data[0].id;
      this.atmEvent.emit(this.atm);
    });    
  }

}
