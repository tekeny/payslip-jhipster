import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayslipSharedModule } from 'app/shared';
import {
  User2Component,
  User2DetailComponent,
  User2UpdateComponent,
  User2DeletePopupComponent,
  User2DeleteDialogComponent,
  user2Route,
  user2PopupRoute
} from './';

const ENTITY_STATES = [...user2Route, ...user2PopupRoute];

@NgModule({
  imports: [PayslipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [User2Component, User2DetailComponent, User2UpdateComponent, User2DeleteDialogComponent, User2DeletePopupComponent],
  entryComponents: [User2Component, User2UpdateComponent, User2DeleteDialogComponent, User2DeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayslipUser2Module {}
