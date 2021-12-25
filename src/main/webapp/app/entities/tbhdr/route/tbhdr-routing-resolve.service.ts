import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITBHDR, TBHDR } from '../tbhdr.model';
import { TBHDRService } from '../service/tbhdr.service';

@Injectable({ providedIn: 'root' })
export class TBHDRRoutingResolveService implements Resolve<ITBHDR> {
  constructor(protected service: TBHDRService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITBHDR> | Observable<never> {
    const id = route.params['thno'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tBHDR: HttpResponse<TBHDR>) => {
          if (tBHDR.body) {
            return of(tBHDR.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TBHDR());
  }
}
