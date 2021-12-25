import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMFSHM, MFSHM } from '../mfshm.model';
import { MFSHMService } from '../service/mfshm.service';

@Injectable({ providedIn: 'root' })
export class MFSHMRoutingResolveService implements Resolve<IMFSHM> {
  constructor(protected service: MFSHMService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMFSHM> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mFSHM: HttpResponse<MFSHM>) => {
          if (mFSHM.body) {
            return of(mFSHM.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MFSHM());
  }
}
