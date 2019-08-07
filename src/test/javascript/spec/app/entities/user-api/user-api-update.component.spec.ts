/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PayslipTestModule } from '../../../test.module';
import { UserApiUpdateComponent } from 'app/entities/user-api/user-api-update.component';
import { UserApiService } from 'app/entities/user-api/user-api.service';
import { UserApi } from 'app/shared/model/user-api.model';

describe('Component Tests', () => {
  describe('UserApi Management Update Component', () => {
    let comp: UserApiUpdateComponent;
    let fixture: ComponentFixture<UserApiUpdateComponent>;
    let service: UserApiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PayslipTestModule],
        declarations: [UserApiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserApiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserApiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserApiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserApi(123);
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
        const entity = new UserApi();
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
