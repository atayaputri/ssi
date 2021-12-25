import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBBROKER, TBBROKER } from '../tbbroker.model';
import { TBBROKERService } from '../service/tbbroker.service';

@Injectable({ providedIn: 'root' })
export class TBBROKERRoutingResolveService implements Resolve<ITBBROKER> {
  constructor(protected service: TBBROKERService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBBROKER> | Observable<never> {
    const id = route.params['brcode'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBBROKER: HttpResponse<TBBROKER>) => {
          if (tBBROKER.body) {
            return of(tBBROKER.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBBROKER());
  }
}
