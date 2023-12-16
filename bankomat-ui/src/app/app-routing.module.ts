import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CardEntryComponent } from './pages/card-entry/card-entry.component';
import { AtmComponent } from './pages/atm/atm.component';
import { CardVerificationComponent } from './components/card-verification/card-verification.component';
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
    ],
  },
  {
    path: 'atm',
    component: AtmComponent,
    children: [
      {
        path: '',
        component: AtmMenuComponent,
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
