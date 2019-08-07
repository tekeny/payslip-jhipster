import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserApi } from 'app/shared/model/user-api.model';
import { UserApiService } from './user-api.service';
import { UserApiComponent } from './user-api.component';
import { UserApiDetailComponent } from './user-api-detail.component';
import { UserApiUpdateComponent } from './user-api-update.component';
import { UserApiDeletePopupComponent } from './user-api-delete-dialog.component';
import { IUserApi } from 'app/shared/model/user-api.model';

@Injectable({ providedIn: 'root' })
export class UserApiResolve implements Resolve<IUserApi> {
  constructor(private service: UserApiService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserApi> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserApi>) => response.ok),
        map((userApi: HttpResponse<UserApi>) => userApi.body)
      );
    }
    return of(new UserApi());
  }
}

export const userApiRoute: Routes = [
  {
    path: '',
    component: UserApiComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserApis'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserApiDetailComponent,
    resolve: {
      userApi: UserApiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserApis'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserApiUpdateComponent,
    resolve: {
      userApi: UserApiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserApis'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserApiUpdateComponent,
    resolve: {
      userApi: UserApiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserApis'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userApiPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserApiDeletePopupComponent,
    resolve: {
      userApi: UserApiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserApis'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
