import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMFSKS, getMFSKSIdentifier } from '../mfsks.model';

export type EntityResponseType = HttpResponse<IMFSKS>;
export type EntityArrayResponseType = HttpResponse<IMFSKS[]>;

@Injectable({ providedIn: 'root' })
export class MFSKSService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mfsks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mFSKS: IMFSKS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mFSKS);
    return this.http
      .post<IMFSKS>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mFSKS: IMFSKS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mFSKS);
    return this.http
      .put<IMFSKS>(`${this.resourceUrl}/${getMFSKSIdentifier(mFSKS) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(mFSKS: IMFSKS): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mFSKS);
    return this.http
      .patch<IMFSKS>(`${this.resourceUrl}/${getMFSKSIdentifier(mFSKS) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMFSKS>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMFSKS[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMFSKSToCollectionIfMissing(mFSKSCollection: IMFSKS[], ...mFSKSToCheck: (IMFSKS | null | undefined)[]): IMFSKS[] {
    const mFSKS: IMFSKS[] = mFSKSToCheck.filter(isPresent);
    if (mFSKS.length > 0) {
      const mFSKSCollectionIdentifiers = mFSKSCollection.map(mFSKSItem => getMFSKSIdentifier(mFSKSItem)!);
      const mFSKSToAdd = mFSKS.filter(mFSKSItem => {
        const mFSKSIdentifier = getMFSKSIdentifier(mFSKSItem);
        if (mFSKSIdentifier == null || mFSKSCollectionIdentifiers.includes(mFSKSIdentifier)) {
          return false;
        }
        mFSKSCollectionIdentifiers.push(mFSKSIdentifier);
        return true;
      });
      return [...mFSKSToAdd, ...mFSKSCollection];
    }
    return mFSKSCollection;
  }

  protected convertDateFromClient(mFSKS: IMFSKS): IMFSKS {
    return Object.assign({}, mFSKS, {
      skdis: mFSKS.skdis?.isValid() ? mFSKS.skdis.format(DATE_FORMAT) : undefined,
      sklmd: mFSKS.sklmd?.isValid() ? mFSKS.sklmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.skdis = res.body.skdis ? dayjs(res.body.skdis) : undefined;
      res.body.sklmd = res.body.sklmd ? dayjs(res.body.sklmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mFSKS: IMFSKS) => {
        mFSKS.skdis = mFSKS.skdis ? dayjs(mFSKS.skdis) : undefined;
        mFSKS.sklmd = mFSKS.sklmd ? dayjs(mFSKS.sklmd) : undefined;
      });
    }
    return res;
  }
}
