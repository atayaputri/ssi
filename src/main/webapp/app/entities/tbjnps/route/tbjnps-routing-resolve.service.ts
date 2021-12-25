import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBJNPS, TBJNPS } from '../tbjnps.model';
import { TBJNPSService } from '../service/tbjnps.service';

@Injectable({ providedIn: 'root' })
export class TBJNPSRoutingResolveService implements Resolve<ITBJNPS> {
  constructor(protected service: TBJNPSService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBJNPS> | Observable<never> {
    const id = route.params['jpscod'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBJNPS: HttpResponse<TBJNPS>) => {
          if (tBJNPS.body) {
            return of(tBJNPS.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBJNPS());
  }
}
