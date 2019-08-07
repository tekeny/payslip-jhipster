import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUser2 } from 'app/shared/model/user-2.model';

type EntityResponseType = HttpResponse<IUser2>;
type EntityArrayResponseType = HttpResponse<IUser2[]>;

@Injectable({ providedIn: 'root' })
export class User2Service {
  public resourceUrl = SERVER_API_URL + 'api/user-2-s';

  constructor(protected http: HttpClient) {}

  create(user2: IUser2): Observable<EntityResponseType> {
    return this.http.post<IUser2>(this.resourceUrl, user2, { observe: 'response' });
  }

  update(user2: IUser2): Observable<EntityResponseType> {
    return this.http.put<IUser2>(this.resourceUrl, user2, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUser2>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUser2[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
