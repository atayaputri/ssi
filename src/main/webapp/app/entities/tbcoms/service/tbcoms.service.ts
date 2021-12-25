import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBCOMS, getTBCOMSIdentifier } from '../tbcoms.model';

export type EntityResponseType = HttpResponse<ITBCOMS>;
export type EntityArrayResponseType = HttpResponse<ITBCOMS[]>;

@Injectable({ providedIn: 'root' })
export class TBCOMSService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbcoms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBCOMS: ITBCOMS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBCOMS);
    return this.http
      .post<ITBCOMS>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBCOMS: ITBCOMS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBCOMS);
    return this.http
      .put<ITBCOMS>(`${this.resourceUrl}/${getTBCOMSIdentifier(tBCOMS) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBCOMS: ITBCOMS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBCOMS);
    return this.http
      .patch<ITBCOMS>(`${this.resourceUrl}/${getTBCOMSIdentifier(tBCOMS) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITBCOMS>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBCOMS[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBCOMSToCollectionIfMissing(tBCOMSCollection: ITBCOMS[], ...tBCOMSToCheck: (ITBCOMS | null | undefined)[]): ITBCOMS[] {
    const tBCOMS: ITBCOMS[] = tBCOMSToCheck.filter(isPresent);
    if (tBCOMS.length > 0) {
      const tBCOMSCollectionIdentifiers = tBCOMSCollection.map(tBCOMSItem => getTBCOMSIdentifier(tBCOMSItem)!);
      const tBCOMSToAdd = tBCOMS.filter(tBCOMSItem => {
        const tBCOMSIdentifier = getTBCOMSIdentifier(tBCOMSItem);
        if (tBCOMSIdentifier == null || tBCOMSCollectionIdentifiers.includes(tBCOMSIdentifier)) {
          return false;
        }
        tBCOMSCollectionIdentifiers.push(tBCOMSIdentifier);
        return true;
      });
      return [...tBCOMSToAdd, ...tBCOMSCollection];
    }
    return tBCOMSCollection;
  }

  protected convertDateFromClient(tBCOMS: ITBCOMS): ITBCOMS {
    return Object.assign({}, tBCOMS, {
      colmd: tBCOMS.colmd?.isValid() ? tBCOMS.colmd.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((tBCOMS: ITBCOMS) => {
        tBCOMS.colmd = tBCOMS.colmd ? dayjs(tBCOMS.colmd) : undefined;
      });
    }
    return res;
  }
}
