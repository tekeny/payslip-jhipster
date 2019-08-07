import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUser2, User2 } from 'app/shared/model/user-2.model';
import { User2Service } from './user-2.service';

@Component({
  selector: 'jhi-user-2-update',
  templateUrl: './user-2-update.component.html'
})
export class User2UpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    userId: [],
    userLogin: [],
    date: []
  });

  constructor(protected user2Service: User2Service, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ user2 }) => {
      this.updateForm(user2);
    });
  }

  updateForm(user2: IUser2) {
    this.editForm.patchValue({
      id: user2.id,
      userId: user2.userId,
      userLogin: user2.userLogin,
      date: user2.date
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const user2 = this.createFromForm();
    if (user2.id !== undefined) {
      this.subscribeToSaveResponse(this.user2Service.update(user2));
    } else {
      this.subscribeToSaveResponse(this.user2Service.create(user2));
    }
  }

  private createFromForm(): IUser2 {
    return {
      ...new User2(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      userLogin: this.editForm.get(['userLogin']).value,
      date: this.editForm.get(['date']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUser2>>) {
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
