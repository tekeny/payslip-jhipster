import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUser2 } from 'app/shared/model/user-2.model';
import { User2Service } from './user-2.service';

@Component({
  selector: 'jhi-user-2-delete-dialog',
  templateUrl: './user-2-delete-dialog.component.html'
})
export class User2DeleteDialogComponent {
  user2: IUser2;

  constructor(protected user2Service: User2Service, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.user2Service.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'user2ListModification',
        content: 'Deleted an user2'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-2-delete-popup',
  template: ''
})
export class User2DeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ user2 }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(User2DeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.user2 = user2;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/user-2', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/user-2', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
