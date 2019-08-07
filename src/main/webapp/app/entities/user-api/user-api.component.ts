import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserApi } from 'app/shared/model/user-api.model';
import { AccountService } from 'app/core';
import { UserApiService } from './user-api.service';

@Component({
  selector: 'jhi-user-api',
  templateUrl: './user-api.component.html'
})
export class UserApiComponent implements OnInit, OnDestroy {
  userApis: IUserApi[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected userApiService: UserApiService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.userApiService
      .query()
      .pipe(
        filter((res: HttpResponse<IUserApi[]>) => res.ok),
        map((res: HttpResponse<IUserApi[]>) => res.body)
      )
      .subscribe(
        (res: IUserApi[]) => {
          this.userApis = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserApis();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserApi) {
    return item.id;
  }

  registerChangeInUserApis() {
    this.eventSubscriber = this.eventManager.subscribe('userApiListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
