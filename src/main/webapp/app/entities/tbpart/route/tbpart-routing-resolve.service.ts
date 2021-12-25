import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBPART, TBPART } from '../tbpart.model';
import { TBPARTService } from '../service/tbpart.service';

@Injectable({ providedIn: 'root' })
export class TBPARTRoutingResolveService implements Resolve<ITBPART> {
  constructor(protected service: TBPARTService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBPART> | Observable<never> {
    const id = route.params['tpacode'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBPART: HttpResponse<TBPART>) => {
          if (tBPART.body) {
            return of(tBPART.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBPART());
  }
}
