import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBBROKER, getTBBROKERIdentifier } from '../tbbroker.model';

export type EntityResponseType = HttpResponse<ITBBROKER>;
export type EntityArrayResponseType = HttpResponse<ITBBROKER[]>;

@Injectable({ providedIn: 'root' })
export class TBBROKERService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbbrokers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBBROKER: ITBBROKER): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBBROKER);
    return this.http
      .post<ITBBROKER>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBBROKER: ITBBROKER): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBBROKER);
    return this.http
      .put<ITBBROKER>(`${this.resourceUrl}/${getTBBROKERIdentifier(tBBROKER) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBBROKER: ITBBROKER): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBBROKER);
    return this.http
      .patch<ITBBROKER>(`${this.resourceUrl}/${getTBBROKERIdentifier(tBBROKER) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITBBROKER>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBBROKER[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBBROKERToCollectionIfMissing(tBBROKERCollection: ITBBROKER[], ...tBBROKERSToCheck: (ITBBROKER | null | undefined)[]): ITBBROKER[] {
    const tBBROKERS: ITBBROKER[] = tBBROKERSToCheck.filter(isPresent);
    if (tBBROKERS.length > 0) {
      const tBBROKERCollectionIdentifiers = tBBROKERCollection.map(tBBROKERItem => getTBBROKERIdentifier(tBBROKERItem)!);
      const tBBROKERSToAdd = tBBROKERS.filter(tBBROKERItem => {
        const tBBROKERIdentifier = getTBBROKERIdentifier(tBBROKERItem);
        if (tBBROKERIdentifier == null || tBBROKERCollectionIdentifiers.includes(tBBROKERIdentifier)) {
          return false;
        }
        tBBROKERCollectionIdentifiers.push(tBBROKERIdentifier);
        return true;
      });
      return [...tBBROKERSToAdd, ...tBBROKERCollection];
    }
    return tBBROKERCollection;
  }

  protected convertDateFromClient(tBBROKER: ITBBROKER): ITBBROKER {
    return Object.assign({}, tBBROKER, {
      brlmd: tBBROKER.brlmd?.isValid() ? tBBROKER.brlmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.brlmd = res.body.brlmd ? dayjs(res.body.brlmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBBROKER: ITBBROKER) => {
        tBBROKER.brlmd = tBBROKER.brlmd ? dayjs(tBBROKER.brlmd) : undefined;
      });
    }
    return res;
  }
}
