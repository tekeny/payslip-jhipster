/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PayslipTestModule } from '../../../test.module';
import { User2UpdateComponent } from 'app/entities/user-2/user-2-update.component';
import { User2Service } from 'app/entities/user-2/user-2.service';
import { User2 } from 'app/shared/model/user-2.model';

describe('Component Tests', () => {
  describe('User2 Management Update Component', () => {
    let comp: User2UpdateComponent;
    let fixture: ComponentFixture<User2UpdateComponent>;
    let service: User2Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PayslipTestModule],
        declarations: [User2UpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(User2UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(User2UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(User2Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new User2(123);
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
        const entity = new User2();
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
