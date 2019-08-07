import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserApi } from 'app/shared/model/user-api.model';

@Component({
  selector: 'jhi-user-api-detail',
  templateUrl: './user-api-detail.component.html'
})
export class UserApiDetailComponent implements OnInit {
  userApi: IUserApi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userApi }) => {
      this.userApi = userApi;
    });
  }

  previousState() {
    window.history.back();
  }
}
