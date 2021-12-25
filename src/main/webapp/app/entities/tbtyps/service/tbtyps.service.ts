import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBTYPS, getTBTYPSIdentifier } from '../tbtyps.model';

export type EntityResponseType = HttpResponse<ITBTYPS>;
export type EntityArrayResponseType = HttpResponse<ITBTYPS[]>;

@Injectable({ providedIn: 'root' })
export class TBTYPSService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbtyps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBTYPS: ITBTYPS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBTYPS);
    return this.http
      .post<ITBTYPS>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBTYPS: ITBTYPS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBTYPS);
    return this.http
      .put<ITBTYPS>(`${this.resourceUrl}/${getTBTYPSIdentifier(tBTYPS) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBTYPS: ITBTYPS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBTYPS);
    return this.http
      .patch<ITBTYPS>(`${this.resourceUrl}/${getTBTYPSIdentifier(tBTYPS) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITBTYPS>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBTYPS[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBTYPSToCollectionIfMissing(tBTYPSCollection: ITBTYPS[], ...tBTYPSToCheck: (ITBTYPS | null | undefined)[]): ITBTYPS[] {
    const tBTYPS: ITBTYPS[] = tBTYPSToCheck.filter(isPresent);
    if (tBTYPS.length > 0) {
      const tBTYPSCollectionIdentifiers = tBTYPSCollection.map(tBTYPSItem => getTBTYPSIdentifier(tBTYPSItem)!);
      const tBTYPSToAdd = tBTYPS.filter(tBTYPSItem => {
        const tBTYPSIdentifier = getTBTYPSIdentifier(tBTYPSItem);
        if (tBTYPSIdentifier == null || tBTYPSCollectionIdentifiers.includes(tBTYPSIdentifier)) {
          return false;
        }
        tBTYPSCollectionIdentifiers.push(tBTYPSIdentifier);
        return true;
      });
      return [...tBTYPSToAdd, ...tBTYPSCollection];
    }
    return tBTYPSCollection;
  }

  protected convertDateFromClient(tBTYPS: ITBTYPS): ITBTYPS {
    return Object.assign({}, tBTYPS, {
      tpslmd: tBTYPS.tpslmd?.isValid() ? tBTYPS.tpslmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.tpslmd = res.body.tpslmd ? dayjs(res.body.tpslmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBTYPS: ITBTYPS) => {
        tBTYPS.tpslmd = tBTYPS.tpslmd ? dayjs(tBTYPS.tpslmd) : undefined;
      });
    }
    return res;
  }
}
