import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PayslipSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [PayslipSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [PayslipSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PayslipSharedModule {
  static forRoot() {
    return {
      ngModule: PayslipSharedModule
    };
  }
}
