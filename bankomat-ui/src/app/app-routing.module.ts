import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CardEntryComponent } from './pages/card-entry/card-entry.component';
import { AccountVerificationComponent } from './components/account-verification/account-verification.component';
import { AtmComponent } from './pages/atm/atm.component';
import { CashWithdrawalComponent } from './components/cash-withdrawal/cash-withdrawal.component';
import { CardVerificationComponent } from './components/card-verification/card-verification.component';
import { CardEjectionComponent } from './components/card-ejection/card-ejection.component';
import { AtmMenuComponent } from './components/atm-menu/atm-menu.component';

const routes: Routes = [
  {
    path: '',
    component: CardEntryComponent,
    children: [
      {
        path: '',
        component: CardVerificationComponent,
      },
      {
        path: 'account-verification',
        component: AccountVerificationComponent,
      },
    ],
  },
  {
    path: 'atm',
    component: AtmComponent,
    children: [
      {
        path: '',
        component: AtmMenuComponent,
      },
      {
        path: 'withdrawal',
        component: CashWithdrawalComponent,
      },
      {
        path: 'card-ejection',
        component: CardEjectionComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
