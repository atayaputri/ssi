import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBCOMSComponent } from '../list/tbcoms.component';
import { TBCOMSDetailComponent } from '../detail/tbcoms-detail.component';
import { TBCOMSUpdateComponent } from '../update/tbcoms-update.component';
import { TBCOMSRoutingResolveService } from './tbcoms-routing-resolve.service';

const tBCOMSRoute: Routes = [
  {
    path: '',
    component: TBCOMSComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':cocode/view',
    component: TBCOMSDetailComponent,
    resolve: {
      tBCOMS: TBCOMSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBCOMSUpdateComponent,
    resolve: {
      tBCOMS: TBCOMSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':cocode/edit',
    component: TBCOMSUpdateComponent,
    resolve: {
      tBCOMS: TBCOMSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBCOMSRoute)],
  exports: [RouterModule],
})
export class TBCOMSRoutingModule {}
