import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PayslipSharedModule } from 'app/shared';
import {
  EmittorComponent,
  EmittorDetailComponent,
  EmittorUpdateComponent,
  EmittorDeletePopupComponent,
  EmittorDeleteDialogComponent,
  emittorRoute,
  emittorPopupRoute
} from './';

const ENTITY_STATES = [...emittorRoute, ...emittorPopupRoute];

@NgModule({
  imports: [PayslipSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EmittorComponent,
    EmittorDetailComponent,
    EmittorUpdateComponent,
    EmittorDeleteDialogComponent,
    EmittorDeletePopupComponent
  ],
  entryComponents: [EmittorComponent, EmittorUpdateComponent, EmittorDeleteDialogComponent, EmittorDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayslipEmittorModule {}
