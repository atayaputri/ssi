import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBKAB, getTBKABIdentifier } from '../tbkab.model';

export type EntityResponseType = HttpResponse<ITBKAB>;
export type EntityArrayResponseType = HttpResponse<ITBKAB[]>;

@Injectable({ providedIn: 'root' })
export class TBKABService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbkabs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBKAB: ITBKAB): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBKAB);
    return this.http
      .post<ITBKAB>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBKAB: ITBKAB): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBKAB);
    return this.http
      .put<ITBKAB>(`${this.resourceUrl}/${getTBKABIdentifier(tBKAB) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBKAB: ITBKAB): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBKAB);
    return this.http
      .patch<ITBKAB>(`${this.resourceUrl}/${getTBKABIdentifier(tBKAB) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITBKAB>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBKAB[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBKABToCollectionIfMissing(tBKABCollection: ITBKAB[], ...tBKABSToCheck: (ITBKAB | null | undefined)[]): ITBKAB[] {
    const tBKABS: ITBKAB[] = tBKABSToCheck.filter(isPresent);
    if (tBKABS.length > 0) {
      const tBKABCollectionIdentifiers = tBKABCollection.map(tBKABItem => getTBKABIdentifier(tBKABItem)!);
      const tBKABSToAdd = tBKABS.filter(tBKABItem => {
        const tBKABIdentifier = getTBKABIdentifier(tBKABItem);
        if (tBKABIdentifier == null || tBKABCollectionIdentifiers.includes(tBKABIdentifier)) {
          return false;
        }
        tBKABCollectionIdentifiers.push(tBKABIdentifier);
        return true;
      });
      return [...tBKABSToAdd, ...tBKABCollection];
    }
    return tBKABCollection;
  }

  protected convertDateFromClient(tBKAB: ITBKAB): ITBKAB {
    return Object.assign({}, tBKAB, {
      kablmd: tBKAB.kablmd?.isValid() ? tBKAB.kablmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.kablmd = res.body.kablmd ? dayjs(res.body.kablmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBKAB: ITBKAB) => {
        tBKAB.kablmd = tBKAB.kablmd ? dayjs(tBKAB.kablmd) : undefined;
      });
    }
    return res;
  }
}
