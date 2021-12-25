import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMFHDR, getMFHDRIdentifier } from '../mfhdr.model';

export type EntityResponseType = HttpResponse<IMFHDR>;
export type EntityArrayResponseType = HttpResponse<IMFHDR[]>;

@Injectable({ providedIn: 'root' })
export class MFHDRService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mfhdrs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mFHDR: IMFHDR): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mFHDR);
    return this.http
      .post<IMFHDR>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mFHDR: IMFHDR): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mFHDR);
    return this.http
      .put<IMFHDR>(`${this.resourceUrl}/${getMFHDRIdentifier(mFHDR) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(mFHDR: IMFHDR): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mFHDR);
    return this.http
      .patch<IMFHDR>(`${this.resourceUrl}/${getMFHDRIdentifier(mFHDR) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMFHDR>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMFHDR[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMFHDRToCollectionIfMissing(mFHDRCollection: IMFHDR[], ...mFHDRSToCheck: (IMFHDR | null | undefined)[]): IMFHDR[] {
    const mFHDRS: IMFHDR[] = mFHDRSToCheck.filter(isPresent);
    if (mFHDRS.length > 0) {
      const mFHDRCollectionIdentifiers = mFHDRCollection.map(mFHDRItem => getMFHDRIdentifier(mFHDRItem)!);
      const mFHDRSToAdd = mFHDRS.filter(mFHDRItem => {
        const mFHDRIdentifier = getMFHDRIdentifier(mFHDRItem);
        if (mFHDRIdentifier == null || mFHDRCollectionIdentifiers.includes(mFHDRIdentifier)) {
          return false;
        }
        mFHDRCollectionIdentifiers.push(mFHDRIdentifier);
        return true;
      });
      return [...mFHDRSToAdd, ...mFHDRCollection];
    }
    return mFHDRCollection;
  }

  protected convertDateFromClient(mFHDR: IMFHDR): IMFHDR {
    return Object.assign({}, mFHDR, {
      hddis: mFHDR.hddis?.isValid() ? mFHDR.hddis.format(DATE_FORMAT) : undefined,
      hdlmd: mFHDR.hdlmd?.isValid() ? mFHDR.hdlmd.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hddis = res.body.hddis ? dayjs(res.body.hddis) : undefined;
      res.body.hdlmd = res.body.hdlmd ? dayjs(res.body.hdlmd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mFHDR: IMFHDR) => {
        mFHDR.hddis = mFHDR.hddis ? dayjs(mFHDR.hddis) : undefined;
        mFHDR.hdlmd = mFHDR.hdlmd ? dayjs(mFHDR.hdlmd) : undefined;
      });
    }
    return res;
  }
}
