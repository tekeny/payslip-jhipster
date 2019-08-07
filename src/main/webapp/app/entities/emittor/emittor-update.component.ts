import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEmittor, Emittor } from 'app/shared/model/emittor.model';
import { EmittorService } from './emittor.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status';
import { IUserApi } from 'app/shared/model/user-api.model';
import { UserApiService } from 'app/entities/user-api';

@Component({
  selector: 'jhi-emittor-update',
  templateUrl: './emittor-update.component.html'
})
export class EmittorUpdateComponent implements OnInit {
  isSaving: boolean;

  laststatuses: IStatus[];

  userapis: IUserApi[];

  editForm = this.fb.group({
    id: [],
    code: [],
    companyName: [null, [Validators.required, Validators.maxLength(48), Validators.pattern('[a-zA-Z0-9 ]')]],
    companySiret: [null, [Validators.required, Validators.minLength(14), Validators.maxLength(14), Validators.pattern('[0-9]')]],
    login: [null, [Validators.required, Validators.pattern('[a-zA-Z0-9.-_]')]],
    canEmit: [],
    canEmitSince: [],
    lastStatus: [],
    createdBy: [null, Validators.required],
    modifiedBy: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected emittorService: EmittorService,
    protected statusService: StatusService,
    protected userApiService: UserApiService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ emittor }) => {
      this.updateForm(emittor);
    });
    this.statusService
      .query({ filter: 'emittor-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStatus[]>) => response.body)
      )
      .subscribe(
        (res: IStatus[]) => {
          if (!this.editForm.get('lastStatus').value || !this.editForm.get('lastStatus').value.id) {
            this.laststatuses = res;
          } else {
            this.statusService
              .find(this.editForm.get('lastStatus').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IStatus>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IStatus>) => subResponse.body)
              )
              .subscribe(
                (subRes: IStatus) => (this.laststatuses = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.userApiService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserApi[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserApi[]>) => response.body)
      )
      .subscribe((res: IUserApi[]) => (this.userapis = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(emittor: IEmittor) {
    this.editForm.patchValue({
      id: emittor.id,
      code: emittor.code,
      companyName: emittor.companyName,
      companySiret: emittor.companySiret,
      login: emittor.login,
      canEmit: emittor.canEmit,
      canEmitSince: emittor.canEmitSince,
      lastStatus: emittor.lastStatus,
      createdBy: emittor.createdBy,
      modifiedBy: emittor.modifiedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const emittor = this.createFromForm();
    if (emittor.id !== undefined) {
      this.subscribeToSaveResponse(this.emittorService.update(emittor));
    } else {
      this.subscribeToSaveResponse(this.emittorService.create(emittor));
    }
  }

  private createFromForm(): IEmittor {
    return {
      ...new Emittor(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      companyName: this.editForm.get(['companyName']).value,
      companySiret: this.editForm.get(['companySiret']).value,
      login: this.editForm.get(['login']).value,
      canEmit: this.editForm.get(['canEmit']).value,
      canEmitSince: this.editForm.get(['canEmitSince']).value,
      lastStatus: this.editForm.get(['lastStatus']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      modifiedBy: this.editForm.get(['modifiedBy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmittor>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackStatusById(index: number, item: IStatus) {
    return item.id;
  }

  trackUserApiById(index: number, item: IUserApi) {
    return item.id;
  }
}
