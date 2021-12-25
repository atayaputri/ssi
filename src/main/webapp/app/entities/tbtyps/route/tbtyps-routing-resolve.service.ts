import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBTYPS, TBTYPS } from '../tbtyps.model';
import { TBTYPSService } from '../service/tbtyps.service';

@Injectable({ providedIn: 'root' })
export class TBTYPSRoutingResolveService implements Resolve<ITBTYPS> {
  constructor(protected service: TBTYPSService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBTYPS> | Observable<never> {
    const id = route.params['tpscod'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBTYPS: HttpResponse<TBTYPS>) => {
          if (tBTYPS.body) {
            return of(tBTYPS.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBTYPS());
  }
}
