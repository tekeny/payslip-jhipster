/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PayslipTestModule } from '../../../test.module';
import { UserApiComponent } from 'app/entities/user-api/user-api.component';
import { UserApiService } from 'app/entities/user-api/user-api.service';
import { UserApi } from 'app/shared/model/user-api.model';

describe('Component Tests', () => {
  describe('UserApi Management Component', () => {
    let comp: UserApiComponent;
    let fixture: ComponentFixture<UserApiComponent>;
    let service: UserApiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PayslipTestModule],
        declarations: [UserApiComponent],
        providers: []
      })
        .overrideTemplate(UserApiComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserApiComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserApiService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserApi(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userApis[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
