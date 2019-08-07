/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PayslipTestModule } from '../../../test.module';
import { User2DeleteDialogComponent } from 'app/entities/user-2/user-2-delete-dialog.component';
import { User2Service } from 'app/entities/user-2/user-2.service';

describe('Component Tests', () => {
  describe('User2 Management Delete Component', () => {
    let comp: User2DeleteDialogComponent;
    let fixture: ComponentFixture<User2DeleteDialogComponent>;
    let service: User2Service;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PayslipTestModule],
        declarations: [User2DeleteDialogComponent]
      })
        .overrideTemplate(User2DeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(User2DeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(User2Service);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
