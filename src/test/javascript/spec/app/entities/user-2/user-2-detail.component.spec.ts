/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayslipTestModule } from '../../../test.module';
import { User2DetailComponent } from 'app/entities/user-2/user-2-detail.component';
import { User2 } from 'app/shared/model/user-2.model';

describe('Component Tests', () => {
  describe('User2 Management Detail Component', () => {
    let comp: User2DetailComponent;
    let fixture: ComponentFixture<User2DetailComponent>;
    const route = ({ data: of({ user2: new User2(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PayslipTestModule],
        declarations: [User2DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(User2DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(User2DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.user2).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
