import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMFSHM, getMFSHMIdentifier } from '../mfshm.model';

export type EntityResponseType = HttpResponse<IMFSHM>;
export type EntityArrayResponseType = HttpResponse<IMFSHM[]>;

@Injectable({ providedIn: 'root' })
export class MFSHMService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mfshms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mFSHM: IMFSHM): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mFSHM);
    return this.http
      .post<IMFSHM>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mFSHM: IMFSHM): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mFSHM);
    return this.http
      .put<IMFSHM>(`${this.resourceUrl}/${getMFSHMIdentifier(mFSHM) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(mFSHM: IMFSHM): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mFSHM);
    return this.http
      .patch<IMFSHM>(`${this.resourceUrl}/${getMFSHMIdentifier(mFSHM) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMFSHM>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMFSHM[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMFSHMToCollectionIfMissing(mFSHMCollection: IMFSHM[], ...mFSHMSToCheck: (IMFSHM | null | undefined)[]): IMFSHM[] {
    const mFSHMS: IMFSHM[] = mFSHMSToCheck.filter(isPresent);
    if (mFSHMS.length > 0) {
      const mFSHMCollectionIdentifiers = mFSHMCollection.map(mFSHMItem => getMFSHMIdentifier(mFSHMItem)!);
      const mFSHMSToAdd = mFSHMS.filter(mFSHMItem => {
        const mFSHMIdentifier = getMFSHMIdentifier(mFSHMItem);
        if (mFSHMIdentifier == null || mFSHMCollectionIdentifiers.includes(mFSHMIdentifier)) {
          return false;
        }
        mFSHMCollectionIdentifiers.push(mFSHMIdentifier);
        return true;
      });
      return [...mFSHMSToAdd, ...mFSHMCollection];
    }
    return mFSHMCollection;
  }

  protected convertDateFromClient(mFSHM: IMFSHM): IMFSHM {
    return Object.assign({}, mFSHM, {
      shdis: mFSHM.shdis?.isValid() ? mFSHM.shdis.format(DATE_FORMAT) : undefined,
      shlmd: mFSHM.shlmd?.isValid() ? mFSHM.shlmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.shdis = res.body.shdis ? dayjs(res.body.shdis) : undefined;
      res.body.shlmd = res.body.shlmd ? dayjs(res.body.shlmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mFSHM: IMFSHM) => {
        mFSHM.shdis = mFSHM.shdis ? dayjs(mFSHM.shdis) : undefined;
        mFSHM.shlmd = mFSHM.shlmd ? dayjs(mFSHM.shlmd) : undefined;
      });
    }
    return res;
  }
}
