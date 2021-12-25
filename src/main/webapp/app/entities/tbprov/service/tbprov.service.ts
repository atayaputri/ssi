import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBPROV, getTBPROVIdentifier } from '../tbprov.model';

export type EntityResponseType = HttpResponse<ITBPROV>;
export type EntityArrayResponseType = HttpResponse<ITBPROV[]>;

@Injectable({ providedIn: 'root' })
export class TBPROVService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbprovs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBPROV: ITBPROV): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBPROV);
    return this.http
      .post<ITBPROV>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBPROV: ITBPROV): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBPROV);
    return this.http
      .put<ITBPROV>(`${this.resourceUrl}/${getTBPROVIdentifier(tBPROV) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBPROV: ITBPROV): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBPROV);
    return this.http
      .patch<ITBPROV>(`${this.resourceUrl}/${getTBPROVIdentifier(tBPROV) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITBPROV>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBPROV[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBPROVToCollectionIfMissing(tBPROVCollection: ITBPROV[], ...tBPROVSToCheck: (ITBPROV | null | undefined)[]): ITBPROV[] {
    const tBPROVS: ITBPROV[] = tBPROVSToCheck.filter(isPresent);
    if (tBPROVS.length > 0) {
      const tBPROVCollectionIdentifiers = tBPROVCollection.map(tBPROVItem => getTBPROVIdentifier(tBPROVItem)!);
      const tBPROVSToAdd = tBPROVS.filter(tBPROVItem => {
        const tBPROVIdentifier = getTBPROVIdentifier(tBPROVItem);
        if (tBPROVIdentifier == null || tBPROVCollectionIdentifiers.includes(tBPROVIdentifier)) {
          return false;
        }
        tBPROVCollectionIdentifiers.push(tBPROVIdentifier);
        return true;
      });
      return [...tBPROVSToAdd, ...tBPROVCollection];
    }
    return tBPROVCollection;
  }

  protected convertDateFromClient(tBPROV: ITBPROV): ITBPROV {
    return Object.assign({}, tBPROV, {
      provlmd: tBPROV.provlmd?.isValid() ? tBPROV.provlmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.provlmd = res.body.provlmd ? dayjs(res.body.provlmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBPROV: ITBPROV) => {
        tBPROV.provlmd = tBPROV.provlmd ? dayjs(tBPROV.provlmd) : undefined;
      });
    }
    return res;
  }
}
