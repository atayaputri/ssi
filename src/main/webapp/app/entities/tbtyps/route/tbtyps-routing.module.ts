import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBTYPSComponent } from '../list/tbtyps.component';
import { TBTYPSDetailComponent } from '../detail/tbtyps-detail.component';
import { TBTYPSUpdateComponent } from '../update/tbtyps-update.component';
import { TBTYPSRoutingResolveService } from './tbtyps-routing-resolve.service';

const tBTYPSRoute: Routes = [
  {
    path: '',
    component: TBTYPSComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':tpscod/view',
    component: TBTYPSDetailComponent,
    resolve: {
      tBTYPS: TBTYPSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBTYPSUpdateComponent,
    resolve: {
      tBTYPS: TBTYPSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':tpscod/edit',
    component: TBTYPSUpdateComponent,
    resolve: {
      tBTYPS: TBTYPSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBTYPSRoute)],
  exports: [RouterModule],
})
export class TBTYPSRoutingModule {}
