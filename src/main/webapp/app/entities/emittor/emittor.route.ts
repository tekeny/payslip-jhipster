import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Emittor } from 'app/shared/model/emittor.model';
import { EmittorService } from './emittor.service';
import { EmittorComponent } from './emittor.component';
import { EmittorDetailComponent } from './emittor-detail.component';
import { EmittorUpdateComponent } from './emittor-update.component';
import { EmittorDeletePopupComponent } from './emittor-delete-dialog.component';
import { IEmittor } from 'app/shared/model/emittor.model';

@Injectable({ providedIn: 'root' })
export class EmittorResolve implements Resolve<IEmittor> {
  constructor(private service: EmittorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmittor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Emittor>) => response.ok),
        map((emittor: HttpResponse<Emittor>) => emittor.body)
      );
    }
    return of(new Emittor());
  }
}

export const emittorRoute: Routes = [
  {
    path: '',
    component: EmittorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Emittors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmittorDetailComponent,
    resolve: {
      emittor: EmittorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Emittors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmittorUpdateComponent,
    resolve: {
      emittor: EmittorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Emittors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmittorUpdateComponent,
    resolve: {
      emittor: EmittorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Emittors'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const emittorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmittorDeletePopupComponent,
    resolve: {
      emittor: EmittorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Emittors'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
