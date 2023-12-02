import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CardEntryComponent } from './pages/card-entry/card-entry.component';
import { AtmComponent } from './pages/atm/atm.component';
import { AccountVerificationComponent } from './components/account-verification/account-verification.component';
import { CashWithdrawalComponent } from './components/cash-withdrawal/cash-withdrawal.component';
import { CardVerificationComponent } from './components/card-verification/card-verification.component';
import { CardEjectionComponent } from './components/card-ejection/card-ejection.component';

@NgModule({
  declarations: [
    AppComponent,
    CardEntryComponent,
    AtmComponent,
    AccountVerificationComponent,
    CashWithdrawalComponent,
    CardVerificationComponent,
    CardEjectionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
