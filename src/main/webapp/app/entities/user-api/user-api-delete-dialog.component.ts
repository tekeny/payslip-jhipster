import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserApi } from 'app/shared/model/user-api.model';
import { UserApiService } from './user-api.service';

@Component({
  selector: 'jhi-user-api-delete-dialog',
  templateUrl: './user-api-delete-dialog.component.html'
})
export class UserApiDeleteDialogComponent {
  userApi: IUserApi;

  constructor(protected userApiService: UserApiService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userApiService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userApiListModification',
        content: 'Deleted an userApi'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-api-delete-popup',
  template: ''
})
export class UserApiDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userApi }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserApiDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userApi = userApi;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/user-api', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/user-api', { outlets: { popup: null } }]);
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
