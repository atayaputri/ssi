import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBJNSHM, getTBJNSHMIdentifier } from '../tbjnshm.model';

export type EntityResponseType = HttpResponse<ITBJNSHM>;
export type EntityArrayResponseType = HttpResponse<ITBJNSHM[]>;

@Injectable({ providedIn: 'root' })
export class TBJNSHMService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbjnshms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBJNSHM: ITBJNSHM): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBJNSHM);
    return this.http
      .post<ITBJNSHM>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBJNSHM: ITBJNSHM): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBJNSHM);
    return this.http
      .put<ITBJNSHM>(`${this.resourceUrl}/${getTBJNSHMIdentifier(tBJNSHM) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBJNSHM: ITBJNSHM): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBJNSHM);
    return this.http
      .patch<ITBJNSHM>(`${this.resourceUrl}/${getTBJNSHMIdentifier(tBJNSHM) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITBJNSHM>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBJNSHM[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBJNSHMToCollectionIfMissing(tBJNSHMCollection: ITBJNSHM[], ...tBJNSHMSToCheck: (ITBJNSHM | null | undefined)[]): ITBJNSHM[] {
    const tBJNSHMS: ITBJNSHM[] = tBJNSHMSToCheck.filter(isPresent);
    if (tBJNSHMS.length > 0) {
      const tBJNSHMCollectionIdentifiers = tBJNSHMCollection.map(tBJNSHMItem => getTBJNSHMIdentifier(tBJNSHMItem)!);
      const tBJNSHMSToAdd = tBJNSHMS.filter(tBJNSHMItem => {
        const tBJNSHMIdentifier = getTBJNSHMIdentifier(tBJNSHMItem);
        if (tBJNSHMIdentifier == null || tBJNSHMCollectionIdentifiers.includes(tBJNSHMIdentifier)) {
          return false;
        }
        tBJNSHMCollectionIdentifiers.push(tBJNSHMIdentifier);
        return true;
      });
      return [...tBJNSHMSToAdd, ...tBJNSHMCollection];
    }
    return tBJNSHMCollection;
  }

  protected convertDateFromClient(tBJNSHM: ITBJNSHM): ITBJNSHM {
    return Object.assign({}, tBJNSHM, {
      jshlmd: tBJNSHM.jshlmd?.isValid() ? tBJNSHM.jshlmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.jshlmd = res.body.jshlmd ? dayjs(res.body.jshlmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBJNSHM: ITBJNSHM) => {
        tBJNSHM.jshlmd = tBJNSHM.jshlmd ? dayjs(tBJNSHM.jshlmd) : undefined;
      });
    }
    return res;
  }
}
