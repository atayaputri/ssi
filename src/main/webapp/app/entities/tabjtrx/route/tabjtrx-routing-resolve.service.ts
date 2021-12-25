import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITABJTRX, TABJTRX } from '../tabjtrx.model';
import { TABJTRXService } from '../service/tabjtrx.service';

@Injectable({ providedIn: 'root' })
export class TABJTRXRoutingResolveService implements Resolve<ITABJTRX> {
  constructor(protected service: TABJTRXService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITABJTRX> | Observable<never> {
    const id = route.params['jtjntx'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tABJTRX: HttpResponse<TABJTRX>) => {
          if (tABJTRX.body) {
            return of(tABJTRX.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TABJTRX());
  }
}
