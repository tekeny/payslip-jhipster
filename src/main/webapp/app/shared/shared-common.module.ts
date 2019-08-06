import { NgModule } from '@angular/core';

import { PayslipSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [PayslipSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [PayslipSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PayslipSharedCommonModule {}
