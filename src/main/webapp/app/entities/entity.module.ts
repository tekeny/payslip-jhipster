import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'status',
        loadChildren: () => import('./status/status.module').then(m => m.PayslipStatusModule)
      },
      {
        path: 'user-api',
        loadChildren: () => import('./user-api/user-api.module').then(m => m.PayslipUserApiModule)
      },
      {
        path: 'emittor',
        loadChildren: () => import('./emittor/emittor.module').then(m => m.PayslipEmittorModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayslipEntityModule {}
