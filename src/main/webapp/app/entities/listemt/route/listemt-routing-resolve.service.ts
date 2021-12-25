import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILISTEMT, LISTEMT } from '../listemt.model';
import { LISTEMTService } from '../service/listemt.service';

@Injectable({ providedIn: 'root' })
export class LISTEMTRoutingResolveService implements Resolve<ILISTEMT> {
  constructor(protected service: LISTEMTService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILISTEMT> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((lISTEMT: HttpResponse<LISTEMT>) => {
          if (lISTEMT.body) {
            return of(lISTEMT.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LISTEMT());
  }
}
