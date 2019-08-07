import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { User2 } from 'app/shared/model/user-2.model';
import { User2Service } from './user-2.service';
import { User2Component } from './user-2.component';
import { User2DetailComponent } from './user-2-detail.component';
import { User2UpdateComponent } from './user-2-update.component';
import { User2DeletePopupComponent } from './user-2-delete-dialog.component';
import { IUser2 } from 'app/shared/model/user-2.model';

@Injectable({ providedIn: 'root' })
export class User2Resolve implements Resolve<IUser2> {
  constructor(private service: User2Service) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUser2> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<User2>) => response.ok),
        map((user2: HttpResponse<User2>) => user2.body)
      );
    }
    return of(new User2());
  }
}

export const user2Route: Routes = [
  {
    path: '',
    component: User2Component,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'User2S'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: User2DetailComponent,
    resolve: {
      user2: User2Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'User2S'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: User2UpdateComponent,
    resolve: {
      user2: User2Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'User2S'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: User2UpdateComponent,
    resolve: {
      user2: User2Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'User2S'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const user2PopupRoute: Routes = [
  {
    path: ':id/delete',
    component: User2DeletePopupComponent,
    resolve: {
      user2: User2Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'User2S'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
