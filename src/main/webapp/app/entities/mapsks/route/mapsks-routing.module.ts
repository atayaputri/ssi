import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MAPSKSComponent } from '../list/mapsks.component';
import { MAPSKSDetailComponent } from '../detail/mapsks-detail.component';
import { MAPSKSUpdateComponent } from '../update/mapsks-update.component';
import { MAPSKSRoutingResolveService } from './mapsks-routing-resolve.service';

const mAPSKSRoute: Routes = [
  {
    path: '',
    component: MAPSKSComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MAPSKSDetailComponent,
    resolve: {
      mAPSKS: MAPSKSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MAPSKSUpdateComponent,
    resolve: {
      mAPSKS: MAPSKSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MAPSKSUpdateComponent,
    resolve: {
      mAPSKS: MAPSKSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mAPSKSRoute)],
  exports: [RouterModule],
})
export class MAPSKSRoutingModule {}
