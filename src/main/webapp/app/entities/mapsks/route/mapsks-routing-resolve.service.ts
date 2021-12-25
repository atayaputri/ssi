import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMAPSKS, MAPSKS } from '../mapsks.model';
import { MAPSKSService } from '../service/mapsks.service';

@Injectable({ providedIn: 'root' })
export class MAPSKSRoutingResolveService implements Resolve<IMAPSKS> {
  constructor(protected service: MAPSKSService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMAPSKS> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mAPSKS: HttpResponse<MAPSKS>) => {
          if (mAPSKS.body) {
            return of(mAPSKS.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MAPSKS());
  }
}
