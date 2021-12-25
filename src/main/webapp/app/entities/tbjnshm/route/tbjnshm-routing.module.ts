import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBJNSHMComponent } from '../list/tbjnshm.component';
import { TBJNSHMDetailComponent } from '../detail/tbjnshm-detail.component';
import { TBJNSHMUpdateComponent } from '../update/tbjnshm-update.component';
import { TBJNSHMRoutingResolveService } from './tbjnshm-routing-resolve.service';

const tBJNSHMRoute: Routes = [
  {
    path: '',
    component: TBJNSHMComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':jshcod/view',
    component: TBJNSHMDetailComponent,
    resolve: {
      tBJNSHM: TBJNSHMRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBJNSHMUpdateComponent,
    resolve: {
      tBJNSHM: TBJNSHMRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':jshcod/edit',
    component: TBJNSHMUpdateComponent,
    resolve: {
      tBJNSHM: TBJNSHMRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBJNSHMRoute)],
  exports: [RouterModule],
})
export class TBJNSHMRoutingModule {}
