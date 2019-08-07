import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUser2 } from 'app/shared/model/user-2.model';
import { AccountService } from 'app/core';
import { User2Service } from './user-2.service';

@Component({
  selector: 'jhi-user-2',
  templateUrl: './user-2.component.html'
})
export class User2Component implements OnInit, OnDestroy {
  user2S: IUser2[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected user2Service: User2Service,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.user2Service
      .query()
      .pipe(
        filter((res: HttpResponse<IUser2[]>) => res.ok),
        map((res: HttpResponse<IUser2[]>) => res.body)
      )
      .subscribe(
        (res: IUser2[]) => {
          this.user2S = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUser2S();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUser2) {
    return item.id;
  }

  registerChangeInUser2S() {
    this.eventSubscriber = this.eventManager.subscribe('user2ListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
