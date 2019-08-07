/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PayslipTestModule } from '../../../test.module';
import { EmittorUpdateComponent } from 'app/entities/emittor/emittor-update.component';
import { EmittorService } from 'app/entities/emittor/emittor.service';
import { Emittor } from 'app/shared/model/emittor.model';

describe('Component Tests', () => {
  describe('Emittor Management Update Component', () => {
    let comp: EmittorUpdateComponent;
    let fixture: ComponentFixture<EmittorUpdateComponent>;
    let service: EmittorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PayslipTestModule],
        declarations: [EmittorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmittorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmittorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmittorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Emittor(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Emittor();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
