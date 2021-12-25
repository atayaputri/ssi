import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITBNEG, getTBNEGIdentifier } from '../tbneg.model';

export type EntityResponseType = HttpResponse<ITBNEG>;
export type EntityArrayResponseType = HttpResponse<ITBNEG[]>;

@Injectable({ providedIn: 'root' })
export class TBNEGService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tbnegs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tBNEG: ITBNEG): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBNEG);
    return this.http
      .post<ITBNEG>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tBNEG: ITBNEG): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBNEG);
    return this.http
      .put<ITBNEG>(`${this.resourceUrl}/${getTBNEGIdentifier(tBNEG) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(tBNEG: ITBNEG): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tBNEG);
    return this.http
      .patch<ITBNEG>(`${this.resourceUrl}/${getTBNEGIdentifier(tBNEG) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITBNEG>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITBNEG[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTBNEGToCollectionIfMissing(tBNEGCollection: ITBNEG[], ...tBNEGSToCheck: (ITBNEG | null | undefined)[]): ITBNEG[] {
    const tBNEGS: ITBNEG[] = tBNEGSToCheck.filter(isPresent);
    if (tBNEGS.length > 0) {
      const tBNEGCollectionIdentifiers = tBNEGCollection.map(tBNEGItem => getTBNEGIdentifier(tBNEGItem)!);
      const tBNEGSToAdd = tBNEGS.filter(tBNEGItem => {
        const tBNEGIdentifier = getTBNEGIdentifier(tBNEGItem);
        if (tBNEGIdentifier == null || tBNEGCollectionIdentifiers.includes(tBNEGIdentifier)) {
          return false;
        }
        tBNEGCollectionIdentifiers.push(tBNEGIdentifier);
        return true;
      });
      return [...tBNEGSToAdd, ...tBNEGCollection];
    }
    return tBNEGCollection;
  }

  protected convertDateFromClient(tBNEG: ITBNEG): ITBNEG {
    return Object.assign({}, tBNEG, {
      neglmd: tBNEG.neglmd?.isValid() ? tBNEG.neglmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.neglmd = res.body.neglmd ? dayjs(res.body.neglmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tBNEG: ITBNEG) => {
        tBNEG.neglmd = tBNEG.neglmd ? dayjs(tBNEG.neglmd) : undefined;
      });
    }
    return res;
  }
}
