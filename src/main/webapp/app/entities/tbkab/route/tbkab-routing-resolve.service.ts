import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBKAB, TBKAB } from '../tbkab.model';
import { TBKABService } from '../service/tbkab.service';

@Injectable({ providedIn: 'root' })
export class TBKABRoutingResolveService implements Resolve<ITBKAB> {
  constructor(protected service: TBKABService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBKAB> | Observable<never> {
    const id = route.params['kabcod'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBKAB: HttpResponse<TBKAB>) => {
          if (tBKAB.body) {
            return of(tBKAB.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBKAB());
  }
}
