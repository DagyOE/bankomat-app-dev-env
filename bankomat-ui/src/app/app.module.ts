import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CardEntryComponent } from './pages/card-entry/card-entry.component';
import { AtmComponent } from './pages/atm/atm.component';
import { CashWithdrawalComponent } from './components/cash-withdrawal/cash-withdrawal.component';
import { CardVerificationComponent } from './components/card-verification/card-verification.component';
import { AtmMenuComponent } from './components/atm-menu/atm-menu.component';
import { ModalWindowComponent } from './components/modal-window/modal-window.component';

@NgModule({
  declarations: [
    AppComponent,
    CardEntryComponent,
    AtmComponent,
    CashWithdrawalComponent,
    CardVerificationComponent,
    AtmMenuComponent,
    ModalWindowComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
