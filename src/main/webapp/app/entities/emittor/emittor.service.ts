import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmittor } from 'app/shared/model/emittor.model';

type EntityResponseType = HttpResponse<IEmittor>;
type EntityArrayResponseType = HttpResponse<IEmittor[]>;

@Injectable({ providedIn: 'root' })
export class EmittorService {
  public resourceUrl = SERVER_API_URL + 'api/emittors';

  constructor(protected http: HttpClient) {}

  create(emittor: IEmittor): Observable<EntityResponseType> {
    return this.http.post<IEmittor>(this.resourceUrl, emittor, { observe: 'response' });
  }

  update(emittor: IEmittor): Observable<EntityResponseType> {
    return this.http.put<IEmittor>(this.resourceUrl, emittor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmittor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmittor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
