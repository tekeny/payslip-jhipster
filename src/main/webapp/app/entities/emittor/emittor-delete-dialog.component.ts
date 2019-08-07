import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmittor } from 'app/shared/model/emittor.model';
import { EmittorService } from './emittor.service';

@Component({
  selector: 'jhi-emittor-delete-dialog',
  templateUrl: './emittor-delete-dialog.component.html'
})
export class EmittorDeleteDialogComponent {
  emittor: IEmittor;

  constructor(protected emittorService: EmittorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.emittorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'emittorListModification',
        content: 'Deleted an emittor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-emittor-delete-popup',
  template: ''
})
export class EmittorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ emittor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmittorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.emittor = emittor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/emittor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/emittor', { outlets: { popup: null } }]);
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
