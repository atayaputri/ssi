import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILISTEMT, getLISTEMTIdentifier } from '../listemt.model';

export type EntityResponseType = HttpResponse<ILISTEMT>;
export type EntityArrayResponseType = HttpResponse<ILISTEMT[]>;

@Injectable({ providedIn: 'root' })
export class LISTEMTService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/listemts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(lISTEMT: ILISTEMT): Observable<EntityResponseType> {
    return this.http.post<ILISTEMT>(this.resourceUrl, lISTEMT, { observe: 'response' });
  }

  update(lISTEMT: ILISTEMT): Observable<EntityResponseType> {
    return this.http.put<ILISTEMT>(`${this.resourceUrl}/${getLISTEMTIdentifier(lISTEMT) as number}`, lISTEMT, { observe: 'response' });
  }

  partialUpdate(lISTEMT: ILISTEMT): Observable<EntityResponseType> {
    return this.http.patch<ILISTEMT>(`${this.resourceUrl}/${getLISTEMTIdentifier(lISTEMT) as number}`, lISTEMT, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILISTEMT>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILISTEMT[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLISTEMTToCollectionIfMissing(lISTEMTCollection: ILISTEMT[], ...lISTEMTSToCheck: (ILISTEMT | null | undefined)[]): ILISTEMT[] {
    const lISTEMTS: ILISTEMT[] = lISTEMTSToCheck.filter(isPresent);
    if (lISTEMTS.length > 0) {
      const lISTEMTCollectionIdentifiers = lISTEMTCollection.map(lISTEMTItem => getLISTEMTIdentifier(lISTEMTItem)!);
      const lISTEMTSToAdd = lISTEMTS.filter(lISTEMTItem => {
        const lISTEMTIdentifier = getLISTEMTIdentifier(lISTEMTItem);
        if (lISTEMTIdentifier == null || lISTEMTCollectionIdentifiers.includes(lISTEMTIdentifier)) {
          return false;
        }
        lISTEMTCollectionIdentifiers.push(lISTEMTIdentifier);
        return true;
      });
      return [...lISTEMTSToAdd, ...lISTEMTCollection];
    }
    return lISTEMTCollection;
  }
}
