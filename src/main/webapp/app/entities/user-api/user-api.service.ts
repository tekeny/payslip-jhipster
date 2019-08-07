import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserApi } from 'app/shared/model/user-api.model';

type EntityResponseType = HttpResponse<IUserApi>;
type EntityArrayResponseType = HttpResponse<IUserApi[]>;

@Injectable({ providedIn: 'root' })
export class UserApiService {
  public resourceUrl = SERVER_API_URL + 'api/user-apis';

  constructor(protected http: HttpClient) {}

  create(userApi: IUserApi): Observable<EntityResponseType> {
    return this.http.post<IUserApi>(this.resourceUrl, userApi, { observe: 'response' });
  }

  update(userApi: IUserApi): Observable<EntityResponseType> {
    return this.http.put<IUserApi>(this.resourceUrl, userApi, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserApi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserApi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
