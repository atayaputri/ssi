import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBPROV, TBPROV } from '../tbprov.model';
import { TBPROVService } from '../service/tbprov.service';

@Injectable({ providedIn: 'root' })
export class TBPROVRoutingResolveService implements Resolve<ITBPROV> {
  constructor(protected service: TBPROVService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBPROV> | Observable<never> {
    const id = route.params['provcod'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBPROV: HttpResponse<TBPROV>) => {
          if (tBPROV.body) {
            return of(tBPROV.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBPROV());
  }
}
