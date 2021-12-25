import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITABFEE, TABFEE } from '../tabfee.model';
import { TABFEEService } from '../service/tabfee.service';

@Injectable({ providedIn: 'root' })
export class TABFEERoutingResolveService implements Resolve<ITABFEE> {
  constructor(protected service: TABFEEService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITABFEE> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tABFEE: HttpResponse<TABFEE>) => {
          if (tABFEE.body) {
            return of(tABFEE.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TABFEE());
  }
}
