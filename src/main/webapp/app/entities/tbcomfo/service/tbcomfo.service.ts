import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBCOMFO, getTBCOMFOIdentifier } from '../tbcomfo.model';

export type EntityResponseType = HttpResponse<ITBCOMFO>;
export type EntityArrayResponseType = HttpResponse<ITBCOMFO[]>;

@Injectable({ providedIn: 'root' })
export class TBCOMFOService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbcomfos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBCOMFO: ITBCOMFO): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBCOMFO);
    return this.http
      .post<ITBCOMFO>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBCOMFO: ITBCOMFO): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBCOMFO);
    return this.http
      .put<ITBCOMFO>(`${this.resourceUrl}/${getTBCOMFOIdentifier(tBCOMFO) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBCOMFO: ITBCOMFO): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBCOMFO);
    return this.http
      .patch<ITBCOMFO>(`${this.resourceUrl}/${getTBCOMFOIdentifier(tBCOMFO) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITBCOMFO>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBCOMFO[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBCOMFOToCollectionIfMissing(tBCOMFOCollection: ITBCOMFO[], ...tBCOMFOSToCheck: (ITBCOMFO | null | undefined)[]): ITBCOMFO[] {
    const tBCOMFOS: ITBCOMFO[] = tBCOMFOSToCheck.filter(isPresent);
    if (tBCOMFOS.length > 0) {
      const tBCOMFOCollectionIdentifiers = tBCOMFOCollection.map(tBCOMFOItem => getTBCOMFOIdentifier(tBCOMFOItem)!);
      const tBCOMFOSToAdd = tBCOMFOS.filter(tBCOMFOItem => {
        const tBCOMFOIdentifier = getTBCOMFOIdentifier(tBCOMFOItem);
        if (tBCOMFOIdentifier == null || tBCOMFOCollectionIdentifiers.includes(tBCOMFOIdentifier)) {
          return false;
        }
        tBCOMFOCollectionIdentifiers.push(tBCOMFOIdentifier);
        return true;
      });
      return [...tBCOMFOSToAdd, ...tBCOMFOCollection];
    }
    return tBCOMFOCollection;
  }

  protected convertDateFromClient(tBCOMFO: ITBCOMFO): ITBCOMFO {
    return Object.assign({}, tBCOMFO, {
      colmd: tBCOMFO.colmd?.isValid() ? tBCOMFO.colmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.colmd = res.body.colmd ? dayjs(res.body.colmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBCOMFO: ITBCOMFO) => {
        tBCOMFO.colmd = tBCOMFO.colmd ? dayjs(tBCOMFO.colmd) : undefined;
      });
    }
    return res;
  }
}
