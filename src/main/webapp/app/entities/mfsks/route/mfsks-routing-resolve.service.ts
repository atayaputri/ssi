import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMFSKS, MFSKS } from '../mfsks.model';
import { MFSKSService } from '../service/mfsks.service';

@Injectable({ providedIn: 'root' })
export class MFSKSRoutingResolveService implements Resolve<IMFSKS> {
  constructor(protected service: MFSKSService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMFSKS> | Observable<never> {
    const id = route.params['skno'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mFSKS: HttpResponse<MFSKS>) => {
          if (mFSKS.body) {
            return of(mFSKS.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MFSKS());
  }
}
