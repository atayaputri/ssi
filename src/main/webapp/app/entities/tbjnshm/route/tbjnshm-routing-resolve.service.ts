import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBJNSHM, TBJNSHM } from '../tbjnshm.model';
import { TBJNSHMService } from '../service/tbjnshm.service';

@Injectable({ providedIn: 'root' })
export class TBJNSHMRoutingResolveService implements Resolve<ITBJNSHM> {
  constructor(protected service: TBJNSHMService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBJNSHM> | Observable<never> {
    const id = route.params['jshcod'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBJNSHM: HttpResponse<TBJNSHM>) => {
          if (tBJNSHM.body) {
            return of(tBJNSHM.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBJNSHM());
  }
}
