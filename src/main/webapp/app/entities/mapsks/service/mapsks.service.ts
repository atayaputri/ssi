import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMAPSKS, getMAPSKSIdentifier } from '../mapsks.model';

export type EntityResponseType = HttpResponse<IMAPSKS>;
export type EntityArrayResponseType = HttpResponse<IMAPSKS[]>;

@Injectable({ providedIn: 'root' })
export class MAPSKSService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mapsks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mAPSKS: IMAPSKS): Observable<EntityResponseType> {
    return this.http.post<IMAPSKS>(this.resourceUrl, mAPSKS, { observe: 'response' });
  }

  update(mAPSKS: IMAPSKS): Observable<EntityResponseType> {
    return this.http.put<IMAPSKS>(`${this.resourceUrl}/${getMAPSKSIdentifier(mAPSKS) as number}`, mAPSKS, { observe: 'response' });
  }

  partialUpdate(mAPSKS: IMAPSKS): Observable<EntityResponseType> {
    return this.http.patch<IMAPSKS>(`${this.resourceUrl}/${getMAPSKSIdentifier(mAPSKS) as number}`, mAPSKS, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMAPSKS>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMAPSKS[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMAPSKSToCollectionIfMissing(mAPSKSCollection: IMAPSKS[], ...mAPSKSToCheck: (IMAPSKS | null | undefined)[]): IMAPSKS[] {
    const mAPSKS: IMAPSKS[] = mAPSKSToCheck.filter(isPresent);
    if (mAPSKS.length > 0) {
      const mAPSKSCollectionIdentifiers = mAPSKSCollection.map(mAPSKSItem => getMAPSKSIdentifier(mAPSKSItem)!);
      const mAPSKSToAdd = mAPSKS.filter(mAPSKSItem => {
        const mAPSKSIdentifier = getMAPSKSIdentifier(mAPSKSItem);
        if (mAPSKSIdentifier == null || mAPSKSCollectionIdentifiers.includes(mAPSKSIdentifier)) {
          return false;
        }
        mAPSKSCollectionIdentifiers.push(mAPSKSIdentifier);
        return true;
      });
      return [...mAPSKSToAdd, ...mAPSKSCollection];
    }
    return mAPSKSCollection;
  }
}
