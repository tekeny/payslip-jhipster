/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayslipTestModule } from '../../../test.module';
import { UserApiDetailComponent } from 'app/entities/user-api/user-api-detail.component';
import { UserApi } from 'app/shared/model/user-api.model';

describe('Component Tests', () => {
  describe('UserApi Management Detail Component', () => {
    let comp: UserApiDetailComponent;
    let fixture: ComponentFixture<UserApiDetailComponent>;
    const route = ({ data: of({ userApi: new UserApi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PayslipTestModule],
        declarations: [UserApiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserApiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserApiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userApi).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
