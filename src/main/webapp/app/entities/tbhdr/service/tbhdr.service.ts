import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBHDR, getTBHDRIdentifier } from '../tbhdr.model';

export type EntityResponseType = HttpResponse<ITBHDR>;
export type EntityArrayResponseType = HttpResponse<ITBHDR[]>;

@Injectable({ providedIn: 'root' })
export class TBHDRService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbhdrs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBHDR: ITBHDR): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBHDR);
    return this.http
      .post<ITBHDR>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBHDR: ITBHDR): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBHDR);
    return this.http
      .put<ITBHDR>(`${this.resourceUrl}/${getTBHDRIdentifier(tBHDR) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBHDR: ITBHDR): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBHDR);
    return this.http
      .patch<ITBHDR>(`${this.resourceUrl}/${getTBHDRIdentifier(tBHDR) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITBHDR>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBHDR[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBHDRToCollectionIfMissing(tBHDRCollection: ITBHDR[], ...tBHDRSToCheck: (ITBHDR | null | undefined)[]): ITBHDR[] {
    const tBHDRS: ITBHDR[] = tBHDRSToCheck.filter(isPresent);
    if (tBHDRS.length > 0) {
      const tBHDRCollectionIdentifiers = tBHDRCollection.map(tBHDRItem => getTBHDRIdentifier(tBHDRItem)!);
      const tBHDRSToAdd = tBHDRS.filter(tBHDRItem => {
        const tBHDRIdentifier = getTBHDRIdentifier(tBHDRItem);
        if (tBHDRIdentifier == null || tBHDRCollectionIdentifiers.includes(tBHDRIdentifier)) {
          return false;
        }
        tBHDRCollectionIdentifiers.push(tBHDRIdentifier);
        return true;
      });
      return [...tBHDRSToAdd, ...tBHDRCollection];
    }
    return tBHDRCollection;
  }

  protected convertDateFromClient(tBHDR: ITBHDR): ITBHDR {
    return Object.assign({}, tBHDR, {
      thdis: tBHDR.thdis?.isValid() ? tBHDR.thdis.format(DATE_FORMAT) : undefined,
      thlmd: tBHDR.thlmd?.isValid() ? tBHDR.thlmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.thdis = res.body.thdis ? dayjs(res.body.thdis) : undefined;
      res.body.thlmd = res.body.thlmd ? dayjs(res.body.thlmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBHDR: ITBHDR) => {
        tBHDR.thdis = tBHDR.thdis ? dayjs(tBHDR.thdis) : undefined;
        tBHDR.thlmd = tBHDR.thlmd ? dayjs(tBHDR.thlmd) : undefined;
      });
    }
    return res;
  }
}
