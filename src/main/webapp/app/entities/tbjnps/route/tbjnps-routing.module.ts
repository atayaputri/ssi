import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBJNPSComponent } from '../list/tbjnps.component';
import { TBJNPSDetailComponent } from '../detail/tbjnps-detail.component';
import { TBJNPSUpdateComponent } from '../update/tbjnps-update.component';
import { TBJNPSRoutingResolveService } from './tbjnps-routing-resolve.service';

const tBJNPSRoute: Routes = [
  {
    path: '',
    component: TBJNPSComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':jpscod/view',
    component: TBJNPSDetailComponent,
    resolve: {
      tBJNPS: TBJNPSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBJNPSUpdateComponent,
    resolve: {
      tBJNPS: TBJNPSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':jpscod/edit',
    component: TBJNPSUpdateComponent,
    resolve: {
      tBJNPS: TBJNPSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBJNPSRoute)],
  exports: [RouterModule],
})
export class TBJNPSRoutingModule {}
