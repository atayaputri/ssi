import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBNEG, TBNEG } from '../tbneg.model';
import { TBNEGService } from '../service/tbneg.service';

@Injectable({ providedIn: 'root' })
export class TBNEGRoutingResolveService implements Resolve<ITBNEG> {
  constructor(protected service: TBNEGService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBNEG> | Observable<never> {
    const id = route.params['negcod'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBNEG: HttpResponse<TBNEG>) => {
          if (tBNEG.body) {
            return of(tBNEG.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBNEG());
  }
}
