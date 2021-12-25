import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBCOMS, TBCOMS } from '../tbcoms.model';
import { TBCOMSService } from '../service/tbcoms.service';

@Injectable({ providedIn: 'root' })
export class TBCOMSRoutingResolveService implements Resolve<ITBCOMS> {
  constructor(protected service: TBCOMSService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBCOMS> | Observable<never> {
    const id = route.params['cocode'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBCOMS: HttpResponse<TBCOMS>) => {
          if (tBCOMS.body) {
            return of(tBCOMS.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBCOMS());
  }
}
