import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBHDRComponent } from '../list/tbhdr.component';
import { TBHDRDetailComponent } from '../detail/tbhdr-detail.component';
import { TBHDRUpdateComponent } from '../update/tbhdr-update.component';
import { TBHDRRoutingResolveService } from './tbhdr-routing-resolve.service';

const tBHDRRoute: Routes = [
  {
    path: '',
    component: TBHDRComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':thno/view',
    component: TBHDRDetailComponent,
    resolve: {
      tBHDR: TBHDRRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBHDRUpdateComponent,
    resolve: {
      tBHDR: TBHDRRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':thno/edit',
    component: TBHDRUpdateComponent,
    resolve: {
      tBHDR: TBHDRRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBHDRRoute)],
  exports: [RouterModule],
})
export class TBHDRRoutingModule {}
