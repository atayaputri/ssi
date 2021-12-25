import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMFHDR, MFHDR } from '../mfhdr.model';
import { MFHDRService } from '../service/mfhdr.service';

@Injectable({ providedIn: 'root' })
export class MFHDRRoutingResolveService implements Resolve<IMFHDR> {
  constructor(protected service: MFHDRService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMFHDR> | Observable<never> {
    const id = route.params['hdno'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mFHDR: HttpResponse<MFHDR>) => {
          if (mFHDR.body) {
            return of(mFHDR.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MFHDR());
  }
}
