import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBJNPS, getTBJNPSIdentifier } from '../tbjnps.model';

export type EntityResponseType = HttpResponse<ITBJNPS>;
export type EntityArrayResponseType = HttpResponse<ITBJNPS[]>;

@Injectable({ providedIn: 'root' })
export class TBJNPSService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbjnps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBJNPS: ITBJNPS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBJNPS);
    return this.http
      .post<ITBJNPS>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBJNPS: ITBJNPS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBJNPS);
    return this.http
      .put<ITBJNPS>(`${this.resourceUrl}/${getTBJNPSIdentifier(tBJNPS) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBJNPS: ITBJNPS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBJNPS);
    return this.http
      .patch<ITBJNPS>(`${this.resourceUrl}/${getTBJNPSIdentifier(tBJNPS) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITBJNPS>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBJNPS[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBJNPSToCollectionIfMissing(tBJNPSCollection: ITBJNPS[], ...tBJNPSToCheck: (ITBJNPS | null | undefined)[]): ITBJNPS[] {
    const tBJNPS: ITBJNPS[] = tBJNPSToCheck.filter(isPresent);
    if (tBJNPS.length > 0) {
      const tBJNPSCollectionIdentifiers = tBJNPSCollection.map(tBJNPSItem => getTBJNPSIdentifier(tBJNPSItem)!);
      const tBJNPSToAdd = tBJNPS.filter(tBJNPSItem => {
        const tBJNPSIdentifier = getTBJNPSIdentifier(tBJNPSItem);
        if (tBJNPSIdentifier == null || tBJNPSCollectionIdentifiers.includes(tBJNPSIdentifier)) {
          return false;
        }
        tBJNPSCollectionIdentifiers.push(tBJNPSIdentifier);
        return true;
      });
      return [...tBJNPSToAdd, ...tBJNPSCollection];
    }
    return tBJNPSCollection;
  }

  protected convertDateFromClient(tBJNPS: ITBJNPS): ITBJNPS {
    return Object.assign({}, tBJNPS, {
      jpslmd: tBJNPS.jpslmd?.isValid() ? tBJNPS.jpslmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.jpslmd = res.body.jpslmd ? dayjs(res.body.jpslmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBJNPS: ITBJNPS) => {
        tBJNPS.jpslmd = tBJNPS.jpslmd ? dayjs(tBJNPS.jpslmd) : undefined;
      });
    }
    return res;
  }
}
