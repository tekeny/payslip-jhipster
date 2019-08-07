/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PayslipTestModule } from '../../../test.module';
import { User2Component } from 'app/entities/user-2/user-2.component';
import { User2Service } from 'app/entities/user-2/user-2.service';
import { User2 } from 'app/shared/model/user-2.model';

describe('Component Tests', () => {
  describe('User2 Management Component', () => {
    let comp: User2Component;
    let fixture: ComponentFixture<User2Component>;
    let service: User2Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PayslipTestModule],
        declarations: [User2Component],
        providers: []
      })
        .overrideTemplate(User2Component, '')
        .compileComponents();

      fixture = TestBed.createComponent(User2Component);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(User2Service);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new User2(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.user2S[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
