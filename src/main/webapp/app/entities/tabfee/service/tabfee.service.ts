import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITABFEE, getTABFEEIdentifier } from '../tabfee.model';

export type EntityResponseType = HttpResponse<ITABFEE>;
export type EntityArrayResponseType = HttpResponse<ITABFEE[]>;

@Injectable({ providedIn: 'root' })
export class TABFEEService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tabfees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tABFEE: ITABFEE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tABFEE);
    return this.http
      .post<ITABFEE>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tABFEE: ITABFEE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tABFEE);
    return this.http
      .put<ITABFEE>(`${this.resourceUrl}/${getTABFEEIdentifier(tABFEE) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tABFEE: ITABFEE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tABFEE);
    return this.http
      .patch<ITABFEE>(`${this.resourceUrl}/${getTABFEEIdentifier(tABFEE) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITABFEE>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITABFEE[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTABFEEToCollectionIfMissing(tABFEECollection: ITABFEE[], ...tABFEESToCheck: (ITABFEE | null | undefined)[]): ITABFEE[] {
    const tABFEES: ITABFEE[] = tABFEESToCheck.filter(isPresent);
    if (tABFEES.length > 0) {
      const tABFEECollectionIdentifiers = tABFEECollection.map(tABFEEItem => getTABFEEIdentifier(tABFEEItem)!);
      const tABFEESToAdd = tABFEES.filter(tABFEEItem => {
        const tABFEEIdentifier = getTABFEEIdentifier(tABFEEItem);
        if (tABFEEIdentifier == null || tABFEECollectionIdentifiers.includes(tABFEEIdentifier)) {
          return false;
        }
        tABFEECollectionIdentifiers.push(tABFEEIdentifier);
        return true;
      });
      return [...tABFEESToAdd, ...tABFEECollection];
    }
    return tABFEECollection;
  }

  protected convertDateFromClient(tABFEE: ITABFEE): ITABFEE {
    return Object.assign({}, tABFEE, {
      felmd: tABFEE.felmd?.isValid() ? tABFEE.felmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.felmd = res.body.felmd ? dayjs(res.body.felmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tABFEE: ITABFEE) => {
        tABFEE.felmd = tABFEE.felmd ? dayjs(tABFEE.felmd) : undefined;
      });
    }
    return res;
  }
}
