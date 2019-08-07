import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayslipSharedModule } from 'app/shared';
import {
  UserApiComponent,
  UserApiDetailComponent,
  UserApiUpdateComponent,
  UserApiDeletePopupComponent,
  UserApiDeleteDialogComponent,
  userApiRoute,
  userApiPopupRoute
} from './';

const ENTITY_STATES = [...userApiRoute, ...userApiPopupRoute];

@NgModule({
  imports: [PayslipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserApiComponent,
    UserApiDetailComponent,
    UserApiUpdateComponent,
    UserApiDeleteDialogComponent,
    UserApiDeletePopupComponent
  ],
  entryComponents: [UserApiComponent, UserApiUpdateComponent, UserApiDeleteDialogComponent, UserApiDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayslipUserApiModule {}
