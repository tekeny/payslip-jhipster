/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PayslipTestModule } from '../../../test.module';
import { EmittorDetailComponent } from 'app/entities/emittor/emittor-detail.component';
import { Emittor } from 'app/shared/model/emittor.model';

describe('Component Tests', () => {
  describe('Emittor Management Detail Component', () => {
    let comp: EmittorDetailComponent;
    let fixture: ComponentFixture<EmittorDetailComponent>;
    const route = ({ data: of({ emittor: new Emittor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PayslipTestModule],
        declarations: [EmittorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmittorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmittorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emittor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
