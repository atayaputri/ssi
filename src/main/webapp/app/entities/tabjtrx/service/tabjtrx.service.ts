import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITABJTRX, getTABJTRXIdentifier } from '../tabjtrx.model';

export type EntityResponseType = HttpResponse<ITABJTRX>;
export type EntityArrayResponseType = HttpResponse<ITABJTRX[]>;

@Injectable({ providedIn: 'root' })
export class TABJTRXService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tabjtrxes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tABJTRX: ITABJTRX): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tABJTRX);
    return this.http
      .post<ITABJTRX>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tABJTRX: ITABJTRX): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tABJTRX);
    return this.http
      .put<ITABJTRX>(`${this.resourceUrl}/${getTABJTRXIdentifier(tABJTRX) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tABJTRX: ITABJTRX): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tABJTRX);
    return this.http
      .patch<ITABJTRX>(`${this.resourceUrl}/${getTABJTRXIdentifier(tABJTRX) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITABJTRX>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITABJTRX[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTABJTRXToCollectionIfMissing(tABJTRXCollection: ITABJTRX[], ...tABJTRXESToCheck: (ITABJTRX | null | undefined)[]): ITABJTRX[] {
    const tABJTRXES: ITABJTRX[] = tABJTRXESToCheck.filter(isPresent);
    if (tABJTRXES.length > 0) {
      const tABJTRXCollectionIdentifiers = tABJTRXCollection.map(tABJTRXItem => getTABJTRXIdentifier(tABJTRXItem)!);
      const tABJTRXESToAdd = tABJTRXES.filter(tABJTRXItem => {
        const tABJTRXIdentifier = getTABJTRXIdentifier(tABJTRXItem);
        if (tABJTRXIdentifier == null || tABJTRXCollectionIdentifiers.includes(tABJTRXIdentifier)) {
          return false;
        }
        tABJTRXCollectionIdentifiers.push(tABJTRXIdentifier);
        return true;
      });
      return [...tABJTRXESToAdd, ...tABJTRXCollection];
    }
    return tABJTRXCollection;
  }

  protected convertDateFromClient(tABJTRX: ITABJTRX): ITABJTRX {
    return Object.assign({}, tABJTRX, {
      jtlmd: tABJTRX.jtlmd?.isValid() ? tABJTRX.jtlmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.jtlmd = res.body.jtlmd ? dayjs(res.body.jtlmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tABJTRX: ITABJTRX) => {
        tABJTRX.jtlmd = tABJTRX.jtlmd ? dayjs(tABJTRX.jtlmd) : undefined;
      });
    }
    return res;
  }
}
