import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBPART, getTBPARTIdentifier } from '../tbpart.model';

export type EntityResponseType = HttpResponse<ITBPART>;
export type EntityArrayResponseType = HttpResponse<ITBPART[]>;

@Injectable({ providedIn: 'root' })
export class TBPARTService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbparts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBPART: ITBPART): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBPART);
    return this.http
      .post<ITBPART>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBPART: ITBPART): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBPART);
    return this.http
      .put<ITBPART>(`${this.resourceUrl}/${getTBPARTIdentifier(tBPART) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBPART: ITBPART): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBPART);
    return this.http
      .patch<ITBPART>(`${this.resourceUrl}/${getTBPARTIdentifier(tBPART) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITBPART>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBPART[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBPARTToCollectionIfMissing(tBPARTCollection: ITBPART[], ...tBPARTSToCheck: (ITBPART | null | undefined)[]): ITBPART[] {
    const tBPARTS: ITBPART[] = tBPARTSToCheck.filter(isPresent);
    if (tBPARTS.length > 0) {
      const tBPARTCollectionIdentifiers = tBPARTCollection.map(tBPARTItem => getTBPARTIdentifier(tBPARTItem)!);
      const tBPARTSToAdd = tBPARTS.filter(tBPARTItem => {
        const tBPARTIdentifier = getTBPARTIdentifier(tBPARTItem);
        if (tBPARTIdentifier == null || tBPARTCollectionIdentifiers.includes(tBPARTIdentifier)) {
          return false;
        }
        tBPARTCollectionIdentifiers.push(tBPARTIdentifier);
        return true;
      });
      return [...tBPARTSToAdd, ...tBPARTCollection];
    }
    return tBPARTCollection;
  }

  protected convertDateFromClient(tBPART: ITBPART): ITBPART {
    return Object.assign({}, tBPART, {
      tpadis: tBPART.tpadis?.isValid() ? tBPART.tpadis.format(DATE_FORMAT) : undefined,
      tpalmd: tBPART.tpalmd?.isValid() ? tBPART.tpalmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.tpadis = res.body.tpadis ? dayjs(res.body.tpadis) : undefined;
      res.body.tpalmd = res.body.tpalmd ? dayjs(res.body.tpalmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBPART: ITBPART) => {
        tBPART.tpadis = tBPART.tpadis ? dayjs(tBPART.tpadis) : undefined;
        tBPART.tpalmd = tBPART.tpalmd ? dayjs(tBPART.tpalmd) : undefined;
      });
    }
    return res;
  }
}
