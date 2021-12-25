import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBCOMFO, TBCOMFO } from '../tbcomfo.model';
import { TBCOMFOService } from '../service/tbcomfo.service';

@Injectable({ providedIn: 'root' })
export class TBCOMFORoutingResolveService implements Resolve<ITBCOMFO> {
  constructor(protected service: TBCOMFOService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBCOMFO> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBCOMFO: HttpResponse<TBCOMFO>) => {
          if (tBCOMFO.body) {
            return of(tBCOMFO.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBCOMFO());
  }
}
