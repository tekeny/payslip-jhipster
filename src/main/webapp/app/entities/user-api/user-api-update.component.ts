import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUserApi, UserApi } from 'app/shared/model/user-api.model';
import { UserApiService } from './user-api.service';

@Component({
  selector: 'jhi-user-api-update',
  templateUrl: './user-api-update.component.html'
})
export class UserApiUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    userId: [],
    userLogin: [],
    date: []
  });

  constructor(protected userApiService: UserApiService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userApi }) => {
      this.updateForm(userApi);
    });
  }

  updateForm(userApi: IUserApi) {
    this.editForm.patchValue({
      id: userApi.id,
      userId: userApi.userId,
      userLogin: userApi.userLogin,
      date: userApi.date
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userApi = this.createFromForm();
    if (userApi.id !== undefined) {
      this.subscribeToSaveResponse(this.userApiService.update(userApi));
    } else {
      this.subscribeToSaveResponse(this.userApiService.create(userApi));
    }
  }

  private createFromForm(): IUserApi {
    return {
      ...new UserApi(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      userLogin: this.editForm.get(['userLogin']).value,
      date: this.editForm.get(['date']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserApi>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
